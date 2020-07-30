package fr.az.crispack.core.dependency;

import fr.az.crispack.core.version.Version;

public interface VersionedDependency extends Dependency
{
	Version version();
	void setVersion(Version version);

	@Override default boolean hasVersion() { return true; }
	@Override default VersionedDependency withVersion() { return this; }
}
