package fr.az.crispack.core.version.versions;

import java.util.HashSet;
import java.util.Set;

import fr.az.crispack.core.version.Version;
import fr.az.crispack.core.version.Versions;
import fr.az.crispack.utils.Utils;

class VersionCombinator
{
	public static VersionRange and(VersionRange version, VersionRange with)
	{
		Version min = Utils.max(version.min(), with.min());
		Version max = Utils.min(version.max(), with.max());
		return new VersionRange(min, max);
	}

	public static VersionsSet and(VersionsSet version, VersionRange	with) {
		return new VersionsSet(VersionCombinator.intersect(version, with)); }

	public static VersionsSet and(VersionsSet version, VersionsSet with) {
		return new VersionsSet(VersionCombinator.intersect(version, with)); }

	public static VersionSet and(VersionSet version, Versions with) {
		return new VersionSet(VersionCombinator.purify(version, with, true)); }

	public static Versions or(VersionRange version, VersionRange with)
	{
		if (version.max().compareTo(with.min()) > 0 && with.max().compareTo(with.min()) > 0)
		{
			Version min = Utils.min(version.min(), with.min());
			Version max = Utils.max(version.max(), with.max());
			return new VersionRange(min, max);
		}

		return new VersionsSet(version, with);
	}

	public static Versions or(VersionsSet version, Versions with)
	{
		for (Versions v : version.versions())
			with = with.or(v);

		return with;
	}

	public static Versions or(VersionSet version, VersionSet with)
	{
		Set<Version> versions = version.versions();
		versions.addAll(with.versions());
		return new VersionSet(versions);
	}

	public static Versions or(VersionSet version, Versions with)
	{
		version = new VersionSet(VersionCombinator.purify(version, with, false));
		return new VersionsSet(version, with);
	}

	private static Set<Version> purify(VersionSet version, Versions with, boolean intersect)
	{
		Set<Version> versions = new HashSet<>(version.versions());
		versions.removeIf(v -> intersect ^ with.has(v));
		return versions;
	}

	private static Set<Versions> intersect(VersionsSet version, Versions with)
	{
		Set<Versions> versions = new HashSet<>();

		for (Versions v : version.versions())
			versions.add(v.and(with));

		return versions;
	}

	private static Set<Versions> intersect(VersionsSet version, VersionsSet with)
	{
		Set<Versions> ranges = new HashSet<>();

		for (Versions range : version.versions())
			ranges.addAll(VersionCombinator.intersect(with, range));

		return ranges;
	}
}
