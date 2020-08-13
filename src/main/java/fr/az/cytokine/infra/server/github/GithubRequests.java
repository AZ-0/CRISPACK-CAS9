package fr.az.cytokine.infra.server.github;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.az.cytokine.api.core.dependency.Dependency;
import fr.az.cytokine.api.core.dependency.GithubDependency;
import fr.az.cytokine.api.core.pack.PackType;
import fr.az.cytokine.infra.server.Net;
import fr.az.cytokine.util.StringSubscriber;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GithubRequests
{
	private final static GithubRequests THIS = new GithubRequests();

	public static GithubRequests get() { return THIS; }

	private final Map<GithubTable.Identity, GithubTable> tables = new HashMap<>();

	private Mono<GithubTable> getTable(PackType type, GithubTable.Identity identity)
	{
		HttpRequest request = Net.request()
				.GET()
				.uri(URI.create("https://api.github.com/repos/%s/%s/tags".formatted(identity.author(), identity.repository())))
				.build();

		return Mono
				.fromFuture(() -> Net.http().sendAsync(request, StringSubscriber.handleString(JSONArray::new)))
				.map(response -> this.handleResponse(type, identity.author(), identity.repository(), response))
				.flatMap(Mono::justOrEmpty)
				.doOnNext(table -> this.tables.put(identity, table));
	}

	private Optional<GithubTable> handleResponse(PackType type, String author, String repo, HttpResponse<JSONArray> response)
	{
		switch (response.statusCode())
		{
			case 200: return this.handleOK(type, author, repo, response);

			case 304: //TODO: Not Modified
			case 404: //TODO: Not Found

			default:
				System.err.println("Unrecognized status code: "+ response.statusCode());
				System.err.println(response.toString());
				return Optional.empty();
		}
	}

	private Optional<GithubTable> handleOK(PackType type, String author, String repo, HttpResponse<JSONArray> response)
	{
		JSONArray body = response.body();
		GithubTable table = new GithubTable(type, author, repo);

		for (int i = 0; i < body.length(); i++)
		{
			JSONObject obj = body.getJSONObject(i);
			String tag = obj.getString("tag");
			String zip = obj.getString("zipball_url");

			JSONObject commit = obj.getJSONObject("commit");
			String sha = commit.getString("sha");

			table.add(tag, sha, zip);
		}

		return Optional.of(table);
	}

	public Flux<Dependency> collectDependencies(GithubDependency source)
	{
		GithubTable.Identity identity = new GithubTable.Identity(source.author(), source.name());

		return Mono
			.justOrEmpty(this.tables.get(identity))
			.switchIfEmpty(this.getTable(source.type(), identity))
			.map(table -> table.get(source.version().raw()))
			.doOnSuccess(tag -> { if (tag == null) System.err.println("Couldn't resolve "+ source); })
			.flatMapMany(GithubTag::getDependencies);
	}
}
