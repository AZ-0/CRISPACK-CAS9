package fr.az.crispack.core.resolve.flatten;

import fr.az.crispack.core.dependency.Dependency;
import fr.az.crispack.core.dependency.DependencyIdentity;
import fr.az.crispack.core.dependency.DependencyNode;
import fr.az.crispack.core.dependency.VersionedDependency;

public  record FlatDependency(DependencyNode node, int depth)
{
	public Dependency dependency() { return this.identity().dependency(); }
	public DependencyIdentity identity() { return this.node.identity(); }

	public boolean hasVersion() { return this.node.hasVersion(); }
	public VersionedDependency withVersion() { return this.node.withVersion(); }

	public int compareTo(FlatDependency other) { return Integer.compare(this.depth, other.depth); }

	@Override public String toString() { return this.node.toString(); }
}
