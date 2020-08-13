package fr.az.cytokine.app.resolve;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import fr.az.cytokine.app.Cytokine;
import fr.az.cytokine.app.Save;
import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.app.dependency.tree.DependencyNode;
import fr.az.cytokine.app.pack.DataPack;
import fr.az.cytokine.app.resolve.flatten.DependencyTreeFlattener;
import fr.az.cytokine.app.resolve.flatten.conflict.VersionConflictException;
import fr.az.cytokine.domain.dependency.DependencyCollector;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CytokineImpl implements Cytokine
{
	public static Builder builder() { return new Builder(); }

	private final DependencyCollector collector;
	private final DependencyTreeFlattener flattener;

	private CytokineImpl(DependencyCollector collector, DependencyTreeFlattener flattener)
	{
		this.collector = collector;
		this.flattener = flattener;
	}

	@Override public Save loadSave(String save) { return null; }
	@Override public void loadAllSaves() {}
	@Override public Set<Save> getLoadedSaves() { return null; }

	@Override public DataPack loadPack(String save, String name) { return null; }
	@Override public void loadAllPacks() {}
	@Override public Set<DataPack> getLoadedDatapacks() { return null; }

	@Override
	public Flux<DependencyNode> createDependencyTree(Save save)
	{
		return Flux.fromIterable(save.packs().values())
				   .flatMap(this::createDependencyTree);
	}

	@Override
	public Flux<DependencyNode> createDependencyTree(DataPack pack)
	{
		return Flux	.fromIterable(pack.dependencies())
					.map(DependencyNode::new)
					.flatMapSequential(this::createTree);
	}

	private Mono<DependencyNode> createTree(DependencyNode dependency)
	{
		return this.buildTree(dependency).then(Mono.just(dependency));
	}

	private Flux<DependencyNode> buildTree(DependencyNode dependency)
	{
		return this.collector.collect(dependency.dependency())
				.map(dependency::makeChild)
				.flatMap(this::createTree);
	}

	@Override
	public List<Dependency> resolveDependencies(DependencyNode tree) throws VersionConflictException
	{
		for (DependencyNode dependency : tree.children())
			dependency.visit(this.flattener);

		return this.flattener.getFlattened();
	}

	@Override
	public void writeDependencies(Save save, Iterable<Dependency> dependencies)
	{

	}

	public static class Builder
	{
		private DependencyCollector collector;
		private DependencyTreeFlattener flattener;

		private Builder() {}

		public CytokineImpl build()
		{
			return new CytokineImpl(this.collector, this.flattener);
		}

		public Builder collector(DependencyCollector collector)
		{
			this.collector = Objects.requireNonNull(collector);
			return this;
		}

		public Builder flattener(DependencyTreeFlattener flattener)
		{
			this.flattener = Objects.requireNonNull(flattener);
			return this;
		}
	}
}
