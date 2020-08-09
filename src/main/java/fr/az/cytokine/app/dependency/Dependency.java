package fr.az.cytokine.app.dependency;

import fr.az.cytokine.app.pack.PackType;
import fr.az.cytokine.server.github.GithubDependency;

import reactor.core.publisher.Flux;

public interface Dependency
{
	PackType type();

	Flux<Dependency> collect();

	boolean isSimilar(Dependency to);
	boolean isSimilar(GithubDependency to);

	default boolean hasVersion() { return false; }
	default VersionedDependency withVersion() { return null; }
}
