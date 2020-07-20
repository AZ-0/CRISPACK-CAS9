package fr.az.registry.core.version;

import fr.az.registry.core.version.versions.EmptyVersions;
import fr.az.registry.core.version.versions.VersionRange;
import fr.az.registry.core.version.versions.VersionSet;
import fr.az.registry.core.version.versions.VersionsSet;

public interface Versions
{
	boolean has(Version version);
	boolean isEmpty();

	//Intersections
	default Versions and(EmptyVersions with) { return with; }
	default Versions or	(EmptyVersions with) { return this; }

	Versions and(Versions with);
	Versions and(VersionRange with);
	Versions and(VersionsSet with);
	Versions and(VersionSet with);

	Versions or (Versions with);
	Versions or (VersionRange with);
	Versions or (VersionsSet with);
	Versions or (VersionSet with);
}
