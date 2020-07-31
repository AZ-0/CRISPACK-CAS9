package fr.az.crispack.core.resolve.flatten;

import fr.az.crispack.core.dependency.VersionedDependency;

public  record FlatDependency(VersionedDependency dependency, int depth)
{
	public int compareTo(FlatDependency other) { return Integer.compare(this.depth, other.depth); }

	@Override public String toString() { return this.dependency().toString(); }
}
