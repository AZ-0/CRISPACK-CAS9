package fr.az.crispack.impl.github;

import fr.az.crispack.core.dependency.Dependency;
import fr.az.crispack.core.dependency.VersionedDependency;
import fr.az.crispack.core.pack.PackType;
import fr.az.crispack.core.version.Version;

import reactor.core.publisher.Flux;

public record GithubDependency(String author, String repository, PackType type, Version version) implements VersionedDependency
{
	@Override public PackType type() { return this.type; }
	@Override public Version version() { return this.version; }

	@Override
	public GithubDependency toVersion(Version version)
	{
		return new GithubDependency(this.author, this.repository, this.type, version);
	}

	@Override public Flux<Dependency> collect() { return GithubRequests.get().collectDependencies(this); }
	@Override public boolean isSimilar(Dependency to) { return to != null && to.isSimilar(this); }

	@Override
	public boolean isSimilar(GithubDependency to)
	{
		return to.author.equals(this.author)
			&& to.repository.equals(this.repository)
			&& to.type.equals(this.type);
	}
}
