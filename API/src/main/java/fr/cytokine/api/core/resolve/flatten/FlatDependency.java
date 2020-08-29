package fr.cytokine.api.core.resolve.flatten;

import fr.cytokine.api.dependency.VersionedDependency;

public  record FlatDependency(VersionedDependency dependency, int depth)
{
	@Override public String toString() { return this.dependency().toString(); }
}
