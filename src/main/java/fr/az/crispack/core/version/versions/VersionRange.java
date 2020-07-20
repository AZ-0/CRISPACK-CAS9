package fr.az.crispack.core.version.versions;

import fr.az.crispack.core.version.Version;
import fr.az.crispack.core.version.Versions;

public record VersionRange(Version min, Version max) implements Versions
{
	public VersionRange(Version single)
	{
		this.min = single;
		this.max = single;
	}

	@Override public boolean has(Version version) { return this.min.compareTo(version) <= 0 && this.max.compareTo(version) >= 0; }
	@Override public boolean isEmpty() { return this.min.compareTo(this.max) > 0; }

	@Override public Versions and(Versions with)		{ return with.and(this); }
	@Override public Versions and(VersionRange with)	{ return VersionCombinator.and(with, this); }
	@Override public Versions and(VersionsSet with)		{ return VersionCombinator.and(with, this); }
	@Override public Versions and(VersionSet with)		{ return VersionCombinator.and(with, this); }

	@Override public Versions or(Versions with)		{ return with.or(this); }
	@Override public Versions or(VersionRange with)	{ return VersionCombinator.or(with, this); }
	@Override public Versions or(VersionsSet with)	{ return VersionCombinator.or(with, this); }
	@Override public Versions or(VersionSet with)	{ return VersionCombinator.or(with, this); }
}
