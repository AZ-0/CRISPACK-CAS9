package fr.az.crispack.core.version.version;

import fr.az.crispack.core.pack.PackIdentity;
import fr.az.crispack.core.pack.resolver.GithubResolver;
import fr.az.crispack.core.version.Version;
import fr.az.crispack.core.version.table.VersionTable;

public record GithubVersion(String name, VersionTable table, int index, String url) implements Version
{
	@Override
	public GithubResolver produceResolver(PackIdentity identity)
	{
		return new GithubResolver(this.url, identity);
	}
}
