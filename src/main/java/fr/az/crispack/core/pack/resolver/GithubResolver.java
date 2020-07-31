package fr.az.crispack.core.pack.resolver;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

import fr.az.crispack.App;
import fr.az.crispack.core.pack.PackIdentity;
import fr.az.crispack.core.pack.content.DataPackContent;
import fr.az.crispack.core.pack.content.PackContent;
import fr.az.crispack.property.Properties;
import fr.az.crispack.util.Util;

import reactor.core.publisher.Mono;

public class GithubResolver implements PackResolver
{
	private final String url;
	private final PackIdentity identity;

	public GithubResolver(String url, PackIdentity identity)
	{
		this.url = url;
		this.identity = identity;
	}

	@Override
	public Mono<PackContent> resolve()
	{
		HttpRequest request = Util.request()
			.GET()
			.uri(URI.create(this.url))
			.build();

		Path download = Properties.getAppPath(this.identity.type(), this.identity.author(), this.identity.name(), this.identity.version().name());

		return Mono.fromFuture(App.http().sendAsync(request, HttpResponse.BodyHandlers.ofFileDownload(download)))
				.map(HttpResponse::body)
				.flatMap(path -> Mono.fromCallable(() -> new DataPackContent(path)));
	}
}
