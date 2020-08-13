package fr.az.cytokine.api.core.resolve.flatten;

import fr.az.cytokine.api.core.dependency.VersionedDependency;

public  record FlatDependency(VersionedDependency dependency, int depth)
{
	@Override public String toString() { return this.dependency().toString(); }
}
