package fr.az.cytokine.app.dependency;

import fr.az.cytokine.app.pack.PackType;

public interface Dependency
{
	PackType type();

	<T> T visit(DependencyVisitor<T> visitor);

	default boolean hasVersion() { return false; }
	default VersionedDependency withVersion() { return null; }
}
