package fr.cytokine.api.dependency;

public interface Dependency
{
	<T> T accept(DependencyVisitor<T> visitor);

	default boolean hasVersion() { return false; }
	default VersionedDependency withVersion() { return null; }
}
