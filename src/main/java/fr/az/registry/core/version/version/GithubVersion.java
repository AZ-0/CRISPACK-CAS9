package fr.az.registry.core.version.version;

import fr.az.registry.core.pack.PackIdentity;
import fr.az.registry.core.pack.resolver.GithubResolver;
import fr.az.registry.core.version.Version;
import fr.az.registry.core.version.table.VersionTable;

public record GithubVersion(String name, VersionTable table, int index, String url) implements Version
{
	@Override
	public GithubResolver produceResolver(PackIdentity identity)
	{
		return new GithubResolver(this.url, identity);
	}
}
