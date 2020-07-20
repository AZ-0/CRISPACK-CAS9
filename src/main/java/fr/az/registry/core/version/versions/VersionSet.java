package fr.az.registry.core.version.versions;

import java.util.Collections;
import java.util.Set;

import fr.az.registry.core.version.Version;
import fr.az.registry.core.version.Versions;

public record VersionSet(Set<Version> versions) implements Versions
{
	public VersionSet
	{
		versions = Collections.unmodifiableSet(versions);
	}

	@Override public boolean has(Version version) { return this.versions.contains(version); }
	@Override public boolean isEmpty() { return this.versions.isEmpty(); }

	@Override public Versions and(Versions with)		{ return VersionCombinator.and(this, with); }
	@Override public Versions and(VersionRange with)	{ return VersionCombinator.and(this, with); }
	@Override public Versions and(VersionsSet with)		{ return VersionCombinator.and(this, with); }
	@Override public Versions and(VersionSet with)		{ return VersionCombinator.and(this, with); }

	@Override public Versions or(Versions with)		{ return VersionCombinator.or(this, with); }
	@Override public Versions or(VersionRange with)	{ return VersionCombinator.or(this, with); }
	@Override public Versions or(VersionsSet with)	{ return VersionCombinator.or(this, with); }
	@Override public Versions or(VersionSet with)	{ return VersionCombinator.or(this, with); }
}
