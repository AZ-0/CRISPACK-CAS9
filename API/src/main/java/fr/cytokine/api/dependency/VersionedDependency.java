package fr.cytokine.api.dependency;

import fr.cytokine.api.Version;

public interface VersionedDependency extends Dependency
{
	Version version();

	default boolean hasSameVersion(VersionedDependency dependency)
	{
		return this.version().equals(dependency.version());
	}

	@Override default boolean hasVersion() { return true; }
	@Override default VersionedDependency withVersion() { return this; }
}
