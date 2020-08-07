package fr.az.cytokine.core.dependency;

import fr.az.cytokine.core.version.Version;

public interface VersionedDependency extends Dependency
{
	Version version();
	VersionedDependency toVersion(Version version);

	default boolean hasSameVersion(VersionedDependency dependency)
	{
		return this.version().equals(dependency.version());
	}

	@Override default boolean hasVersion() { return true; }
	@Override default VersionedDependency withVersion() { return this; }
}
