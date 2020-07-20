package fr.az.crispack.core.version.version;

import java.nio.file.Path;

import fr.az.crispack.core.pack.PackIdentity;
import fr.az.crispack.core.pack.content.DataPackContent;
import fr.az.crispack.core.pack.resolver.PackResolver;
import fr.az.crispack.core.version.Version;
import fr.az.crispack.core.version.table.VersionTable;

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
