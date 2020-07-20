package fr.az.crispack.core.version.versions;

import java.util.Collections;
import java.util.Set;

import fr.az.crispack.core.version.Version;
import fr.az.crispack.core.version.Versions;
import fr.az.crispack.utils.Utils;

public record VersionsSet(Set<Versions> versions) implements Versions
{
	public VersionsSet(Versions... versions)
	{
		this(Utils.setOf(versions));
	}

	public VersionsSet
	{
		versions.removeIf(Versions::isEmpty);
		versions = Collections.unmodifiableSet(versions);
	}

	@Override
	public boolean has(Version version)
	{
		for (Versions range : this.versions)
			if (range.has(version))
				return true;

		return false;
	}

	@Override public boolean isEmpty() { return this.versions.isEmpty(); }

	@Override public Versions and(Versions with)		{ return with.and(this); }
	@Override public Versions and(VersionRange with)	{ return VersionCombinator.and(this, with); }
	@Override public Versions and(VersionsSet with)		{ return VersionCombinator.and(this, with); }
	@Override public Versions and(VersionSet with)		{ return VersionCombinator.and(with, this); }

	@Override public Versions or(Versions with)		{ return VersionCombinator.or(this, with); }
	@Override public Versions or(VersionRange with)	{ return VersionCombinator.or(this, with); }
	@Override public Versions or(VersionsSet with)	{ return VersionCombinator.or(this, with); }
	@Override public Versions or(VersionSet with)	{ return VersionCombinator.or(this, with); }
}
