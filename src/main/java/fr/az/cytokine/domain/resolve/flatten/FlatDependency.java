package fr.az.cytokine.domain.resolve.flatten;

import fr.az.cytokine.domain.dependency.VersionedDependency;

public  record FlatDependency(VersionedDependency dependency, int depth)
{
	public int compareTo(FlatDependency other) { return Integer.compare(this.depth, other.depth); }

	@Override public String toString() { return this.dependency().toString(); }
}
