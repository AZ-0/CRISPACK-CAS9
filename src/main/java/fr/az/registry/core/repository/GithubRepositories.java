package fr.az.registry.core.repository;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.az.registry.App;
import fr.az.registry.core.version.Versions;
import fr.az.registry.core.version.table.VersionList;
import fr.az.registry.core.version.table.VersionTable;
import fr.az.registry.core.version.version.GithubVersion;
import fr.az.registry.property.Header;

import reactor.core.publisher.Mono;

public class GithubRepositories
{
	private final String user;
	private final Map<String, Versions> repos;

	public GithubRepositories(String user)
	{
		this.user = user;
		this.repos = new HashMap<>();
	}

	public GithubRepositories(String user, Map<String, ? extends Versions> repos)
	{
		this.user = user;
		this.repos = new HashMap<>(repos);
	}

	public Mono<VersionTable> getTable(String repo)
	{
		HttpRequest request = HttpRequest.newBuilder()
			.GET()
			.uri(URI.create("https://api.github.com/repos/%s/%s/tags".formatted(this.user, repo)))
			.setHeader(Header.USER_AGENT.header(), Header.USER_AGENT.value())
			.build();

		return Mono
				.fromFuture(App.http().sendAsync(request, HttpResponse.BodyHandlers.ofString()))
				.map(this::handleResponse)
				.flatMap(Mono::justOrEmpty);
	}

	private Optional<VersionTable> handleResponse(HttpResponse<String> response)
	{
		switch (response.statusCode())
		{
			case 200:
				JSONArray body = new JSONArray(response.body());
				VersionTable table = new VersionList();

				for (int i = 0; i < body.length(); i++)
				{
					JSONObject tag	= body.getJSONObject(i);
					String name		= tag.getString("name");
					String url		= tag.getString("zipball_url");

					table.register(new GithubVersion(name, table, i, url));
				}

				return Optional.of(table);

			case 304: //TODO: Not Modified
			case 404: //TODO: Not Found

			default:
				App.logger().info("Unrecognized status code: "+ response.statusCode());
				return Optional.empty();
		}
	}

	public void put(String repository, Versions target) { this.repos.put(repository, target); }
	public boolean has(String repository) { return this.repos.containsKey(repository); }

	public void and(String repository, Versions combineWith) { this.combine(repository, combineWith, Versions::and); }
	public void or (String repository, Versions combineWith) { this.combine(repository, combineWith, Versions::or);  }

	public void combine(String repository, Versions with, BiFunction<Versions, Versions, Versions> combinator)
	{
		Versions old = this.repos.putIfAbsent(repository, with);

		if (old != null)
			this.repos.put(repository, combinator.apply(old, with));
	}

	public String user() { return this.user; }
	public Map<String, Versions> repos() { return this.repos; }
}
