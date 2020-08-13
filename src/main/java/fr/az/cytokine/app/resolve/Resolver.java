package fr.az.cytokine.app.resolve;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.app.dependency.tree.DependencyNode;
import fr.az.cytokine.app.resolve.flatten.DependencyTreeFlattener;
import fr.az.cytokine.domain.DependencyCollector;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Resolver
{
	public static Builder builder() { return new Builder(); }

	private final DependencyCollector collector;
	private final DependencyTreeFlattener flattener;

	private Resolver(DependencyCollector collector, DependencyTreeFlattener flattener)
	{
		this.collector = collector;
		this.flattener = flattener;
	}

	public void resolve(Iterable<? extends Dependency> dependencies)
	{
		DependencyNode root = new DependencyNode(null);

		Flux.fromIterable(dependencies)
			.map(root::makeChild)
			.flatMapSequential(this::createTree)
			.then(Mono.just(this.flatten(root)))
			.flatMapIterable(Function.identity())
			//TODO: Resolve gathered dependencies
			;
	}

	public Mono<DependencyNode> createTree(DependencyNode dependency)
	{
		return this.buildTree(dependency).then(Mono.just(dependency));
	}

	private Flux<DependencyNode> buildTree(DependencyNode dependency)
	{
		return this.collector.collect(dependency.dependency())
				.map(dependency::makeChild)
				.flatMap(this::createTree);
	}

	private List<Dependency> flatten(DependencyNode root)
	{
		for (DependencyNode dependency : root.children())
			dependency.visit(this.flattener);

		return this.flattener.getFlattened();
	}

	public static class Builder
	{
		private DependencyCollector collector;
		private DependencyTreeFlattener flattener;

		private Builder() {}

		public Resolver build()
		{
			return new Resolver(this.collector, this.flattener);
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
