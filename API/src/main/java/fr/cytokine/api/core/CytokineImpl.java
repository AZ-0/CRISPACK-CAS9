package fr.cytokine.api.core;

import fr.cytokine.api.Cytokine;
import fr.cytokine.api.DependencyHolder;
import fr.cytokine.api.DependencyTree;
import fr.cytokine.api.Save;
import fr.cytokine.api.core.resolve.flatten.conflict.VersionConflictException;
import fr.cytokine.api.dependency.Dependency;

import java.util.List;
import java.util.Set;

public class CytokineImpl implements Cytokine
{
	@Override
	public Save getSave(String save) {
		return null;
	}

	@Override
	public Set<DependencyHolder> getDependencies(Save save) {
		return null;
	}

	@Override
	public DependencyTree createDependencyTree(Save save) {
		return null;
	}

	@Override
	public DependencyTree createDependencyTree(DependencyHolder dependencies) {
		return null;
	}

	@Override
	public List<Dependency> resolveDependencies(DependencyTree tree) throws VersionConflictException {
		return null;
	}
}
