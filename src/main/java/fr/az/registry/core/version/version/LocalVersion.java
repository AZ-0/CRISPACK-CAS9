package fr.az.registry.core.version.version;

import java.nio.file.Path;

import fr.az.registry.core.pack.PackIdentity;
import fr.az.registry.core.pack.content.DataPackContent;
import fr.az.registry.core.pack.resolver.PackResolver;
import fr.az.registry.core.version.Version;
import fr.az.registry.core.version.table.VersionTable;

import reactor.core.publisher.Mono;

public record LocalVersion(String name, String url) implements Version
{
	@Override
	public VersionTable table()
	{
		return null;
	}

	@Override public int index() { return 0; }

	@Override
	public PackResolver produceResolver(PackIdentity identity)
	{
		return () -> Mono.fromCallable(() -> new DataPackContent(Path.of(this.url)));
	}
}
