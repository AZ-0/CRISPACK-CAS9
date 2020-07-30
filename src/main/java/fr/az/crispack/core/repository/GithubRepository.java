package fr.az.crispack.core.repository;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.az.crispack.App;
import fr.az.crispack.core.Repository;
import fr.az.crispack.core.dependency.Dependency;
import fr.az.crispack.core.version.Version;
import fr.az.crispack.property.Header;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GithubRepository implements Repository
{
	private final String user;

	public GithubRepository(String user)
	{
		this.user = user;
	}

	public Mono<List<Version>> getTable(String repo)
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

	private Optional<List<Version>> handleResponse(HttpResponse<String> response)
	{
		switch (response.statusCode())
		{
			case 200:
				JSONArray body = new JSONArray(response.body());
				List<Version> table = new ArrayList<>();

				for (int i = 0; i < body.length(); i++)
				{
					JSONObject tag	= body.getJSONObject(i);
					String name		= tag.getString("name");
					String url		= tag.getString("zipball_url");

					table.add(null);
				}

				return Optional.of(table);

			case 304: //TODO: Not Modified
			case 404: //TODO: Not Found

			default:
				App.logger().info("Unrecognized status code: "+ response.statusCode());
				return Optional.empty();
		}
	}

	public String user() { return this.user; }

	@Override
	public Flux<Dependency> collect(Dependency source)
	{
		return Flux.empty();
	}
}
