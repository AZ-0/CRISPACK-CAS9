package fr.az.cytokine.api.core.dependency;

import fr.az.cytokine.api.core.pack.PackType;

public interface Dependency
{
	PackType type();

	<T> T accept(DependencyVisitor<T> visitor);

	default boolean hasVersion() { return false; }
	default VersionedDependency withVersion() { return null; }
}
