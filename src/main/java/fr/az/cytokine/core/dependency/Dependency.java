package fr.az.cytokine.core.dependency;

import fr.az.cytokine.core.pack.PackType;
import fr.az.cytokine.impl.github.GithubDependency;

import reactor.core.publisher.Flux;

public interface Dependency
{
	PackType type();

	Flux<Dependency> collect();

	boolean isSimilar(Dependency to);
	boolean isSimilar(GithubDependency to);

	default boolean hasVersion() { return false; }
	default VersionedDependency withVersion() { return null; }

	enum Kind
	{
		GITHUB,
		GITLAB,
		LINK,
		PATH,
		;

		public String key() { return this.name().toLowerCase(); }
	}
}
