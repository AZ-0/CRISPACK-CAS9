package fr.cytokine.api;

import fr.cytokine.api.core.resolve.flatten.conflict.VersionConflictException;
import fr.cytokine.api.dependency.Dependency;

import java.util.List;
import java.util.Set;

public interface Cytokine
{
	Save getSave(String save);
	Set<DependencyHolder> getDependencies(Save save);

	/**
	 * Shortcut method to create a dependency tree from a save's dependencies
	 * @param save
	 * @return
	 */
	DependencyTree createDependencyTree(Save save);
	DependencyTree createDependencyTree(DependencyHolder dependencies);
	List<Dependency> resolveDependencies(DependencyTree tree) throws VersionConflictException;
}
