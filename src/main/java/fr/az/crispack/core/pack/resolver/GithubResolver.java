package fr.az.crispack.core.pack.resolver;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import fr.az.crispack.App;
import fr.az.crispack.core.pack.PackIdentity;
import fr.az.crispack.core.pack.PackType;
import fr.az.crispack.core.pack.content.DataPackContent;
import fr.az.crispack.core.pack.content.PackContent;
import fr.az.crispack.property.Header;
import fr.az.crispack.property.Properties;

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
		HttpRequest request = HttpRequest.newBuilder()
			.GET()
			.uri(URI.create(this.url))
			.header(Header.USER_AGENT.header(), Header.USER_AGENT.value())
			.build();

		return Mono.fromFuture(App.http().sendAsync(request, HttpResponse.BodyHandlers.ofInputStream()))
				.map(HttpResponse::body)
				.map(ZipInputStream::new)
				.flatMap(zis -> Mono.fromCallable(() -> this.resolve(this.identity, zis)));
	}

	private PackContent resolve(PackIdentity identity, ZipInputStream zis) throws IOException
	{
		Path path = this.getFolder(identity.type()).resolve("%s/%s/%s".formatted
		(
			identity.author(),
			identity.name(),
			identity.version().name()
		));

		while (zis.getNextEntry() != null)
			try (var bos = new BufferedOutputStream(new ZipOutputStream(new FileOutputStream(path.toFile()))))
			{
				while (zis.available() != 0)
					bos.write(zis.readNBytes(1024));
			} catch (IOException e)
			{
				App.logger().error(e);
			}

		return new DataPackContent(path);
	}

	private Path getFolder(PackType type) throws IOException
	{
		switch (type)
		{
			case DATA_PACK:			return Properties.datapacksFolder();
			case RESOURCES_PACK:	return Properties.resourcepacksFolder();
		}

		throw new IOException("Unhandled pack type: "+ type.toString());
	}
}
