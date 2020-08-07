package fr.az.cytokine.core.resolve.flatten;

import fr.az.cytokine.core.dependency.VersionedDependency;

public  record FlatDependency(VersionedDependency dependency, int depth)
{
	public int compareTo(FlatDependency other) { return Integer.compare(this.depth, other.depth); }

	@Override public String toString() { return this.dependency().toString(); }
}
