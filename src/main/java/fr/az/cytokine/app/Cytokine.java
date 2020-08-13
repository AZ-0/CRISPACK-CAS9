package fr.az.cytokine.app;

import java.util.List;
import java.util.Set;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.app.dependency.tree.DependencyNode;
import fr.az.cytokine.app.pack.DataPack;
import fr.az.cytokine.app.resolve.flatten.conflict.VersionConflictException;

import reactor.core.publisher.Flux;

public interface Cytokine
{
	Save loadSave(String save);
	void loadAllSaves();
	Set<Save> getLoadedSaves();

	DataPack loadPack(String save, String name);
	void loadAllPacks();
	Set<DataPack> getLoadedDatapacks();

	Flux<DependencyNode> createDependencyTree(Save save);
	Flux<DependencyNode> createDependencyTree(DataPack pack);
	List<Dependency> resolveDependencies(DependencyNode tree) throws VersionConflictException;
	void writeDependencies(Save save, Iterable<Dependency> dependencies);
}
