package fr.az.cytokine.app.resolve.flatten;

import fr.az.cytokine.app.dependency.VersionedDependency;

public  record FlatDependency(VersionedDependency dependency, int depth)
{
	@Override public String toString() { return this.dependency().toString(); }
}
