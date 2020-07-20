package fr.az.crispack.core.version.versions;

import fr.az.crispack.core.version.Version;
import fr.az.crispack.core.version.Versions;

public record EmptyVersions() implements Versions
{
	@Override public boolean has(Version version) { return false; }
	@Override public boolean isEmpty() { return true; }

	@Override public Versions and(Versions with)		{ return this; }
	@Override public Versions and(VersionRange with)	{ return this; }
	@Override public Versions and(VersionsSet with)		{ return this; }
	@Override public Versions and(VersionSet with)		{ return this; }

	@Override public Versions or(Versions with)		{ return with; }
	@Override public Versions or(VersionRange with)	{ return with; }
	@Override public Versions or(VersionsSet with)	{ return with; }
	@Override public Versions or(VersionSet with)	{ return with; }
}
