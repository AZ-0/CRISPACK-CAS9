package fr.az.cytokine.server.github;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.app.dependency.VersionedDependency;
import fr.az.cytokine.app.pack.PackType;
import fr.az.cytokine.app.version.Version;

import reactor.core.publisher.Flux;

public record GithubDependency(String author, String repository, PackType type, Version version) implements VersionedDependency
{
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

	@Override
	public String toString()
	{
		return "github[%s, %s:%s:%s]".formatted(this.type.dirName(), this.author, this.repository, this.version);
	}
}
