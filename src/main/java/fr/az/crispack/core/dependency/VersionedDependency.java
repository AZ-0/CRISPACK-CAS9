package fr.az.crispack.core.dependency;

import fr.az.crispack.core.version.Version;

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
