package fr.az.crispack.core.dependency;

import fr.az.crispack.core.pack.PackType;

public interface Dependency
{
	PackType type();

	default boolean hasVersion() { return false; }
	default VersionedDependency withVersion() { return null; }
}
