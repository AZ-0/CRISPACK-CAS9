package fr.az.crispack.impl.github;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.az.crispack.App;
import fr.az.crispack.core.dependency.Dependency;
import fr.az.crispack.util.StringSubscriber;
import fr.az.crispack.util.Utils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GithubRequests
{
	private final static GithubRequests THIS = new GithubRequests();

	public static GithubRequests get() { return THIS; }

	private final Map<GithubTable.Identity, GithubTable> tables = new HashMap<>();

	public Mono<GithubTable> getTable(String author, String repo)
	{
		return Mono.fromSupplier
		(
			() -> Utils.request()
				.GET()
				.uri(URI.create("https://api.github.com/repos/%s/%s/tags".formatted(author, repo)))
				.build()
		)
		.map(request -> App.http().sendAsync(request, StringSubscriber.handleString(JSONArray::new)))
		.flatMap(Mono::fromFuture)
		.map(this::handleResponse)
		.flatMap(Mono::justOrEmpty)
		.doOnNext(table -> this.tables.put(new GithubTable.Identity(author, repo), table));
	}

	private Optional<GithubTable> handleResponse(HttpResponse<JSONArray> response)
	{
		switch (response.statusCode())
		{
			case 200: return this.handleOK(response);

			case 304: //TODO: Not Modified
			case 404: //TODO: Not Found

			default:
				App.logger().warning("Unrecognized status code: "+ response.statusCode());
				App.logger().warning(response.toString());
				return Optional.empty();
		}
	}

	private Optional<GithubTable> handleOK(HttpResponse<JSONArray> response)
	{
		JSONArray body = response.body();
		GithubTable table = new GithubTable();

		for (int i = 0; i < body.length(); i++)
		{
			JSONObject obj = body.getJSONObject(i);
			String tag = obj.getString("tag");
			String zip = obj.getString("zipball_url");

			JSONObject commit = obj.getJSONObject("commit");
			String sha = commit.getString("sha");

			table.add(new GithubTag(tag, sha, zip));
		}

		return Optional.of(table);
	}

	public Flux<Dependency> collectDependencies(GithubDependency source)
	{
		GithubTable.Identity identity = new GithubTable.Identity(source.author(), source.repository());

		return Mono
			.justOrEmpty(this.tables.get(identity))
			.switchIfEmpty(this.getTable(source.author(), source.repository()))
			.map(table -> table.get(source.version().name()))
			.flatMapMany(GithubTag::getDependencies);
	}
}
