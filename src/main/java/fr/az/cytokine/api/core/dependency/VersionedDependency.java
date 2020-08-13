package fr.az.cytokine.api.core.dependency;

import fr.az.cytokine.api.core.version.Version;

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
