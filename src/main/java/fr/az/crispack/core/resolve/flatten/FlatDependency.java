package fr.az.crispack.core.resolve.flatten;

import fr.az.crispack.core.dependency.Dependency;
import fr.az.crispack.core.dependency.VersionedDependency;

public  record FlatDependency(Dependency dependency, int depth)
{
	public boolean hasVersion() { return this.dependency().hasVersion(); }
	public VersionedDependency withVersion() { return this.dependency().withVersion(); }

	public int compareTo(FlatDependency other) { return Integer.compare(this.depth, other.depth); }

	@Override public String toString() { return this.dependency().toString(); }
}
