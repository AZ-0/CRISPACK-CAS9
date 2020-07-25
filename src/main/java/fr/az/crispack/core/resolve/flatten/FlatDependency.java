package fr.az.crispack.core.resolve.flatten;

import fr.az.crispack.core.Repository;
import fr.az.crispack.core.resolve.Dependency;
import fr.az.crispack.core.resolve.DependencyIdentity;

public  record FlatDependency(Dependency dependency, int depth) implements Comparable<FlatDependency>
{
	public Repository repository() { return this.identity().repository(); }
	public DependencyIdentity identity() { return this.dependency.getIdentity(); }

	@Override public int compareTo(FlatDependency other) { return Integer.compare(this.depth, other.depth); }

	@Override public String toString() { return this.dependency.toString(); }
}
