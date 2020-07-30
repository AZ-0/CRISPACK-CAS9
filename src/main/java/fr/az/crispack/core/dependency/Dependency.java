package fr.az.crispack.core.dependency;

import fr.az.crispack.core.pack.PackType;
import fr.az.crispack.impl.github.GithubDependency;

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
