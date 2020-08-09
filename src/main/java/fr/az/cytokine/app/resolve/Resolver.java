package fr.az.cytokine.app.resolve;

import java.util.Collections;
import java.util.List;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.app.dependency.DependencyNode;
import fr.az.cytokine.app.resolve.flatten.DependencyTreeFlattener;
import fr.az.cytokine.util.trees.visit.VisitSignal;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Resolver
{
	public static Builder builder() { return new Builder(); }

	private final DependencyTreeFlattener flattener;

	private Resolver(DependencyTreeFlattener flattener)
	{
		this.flattener = flattener;
	}

	public void resolve(Iterable<? extends Dependency> dependencies)
	{
		Flux.fromIterable(dependencies)
			.map(DependencyNode::root)
			.flatMapSequential(Resolver::createTree)
			.buffer()
			.flatMapIterable(this::flatten)
			//TODO: Resolve gathered dependencies
			;
	}

	public static Mono<DependencyNode> createTree(DependencyNode dependency) { return buildTree(dependency).then(Mono.just(dependency)); }
	private static Flux<DependencyNode> buildTree(DependencyNode dependency)
	{
		return dependency.collect().flatMap(Resolver::createTree);
	}

	private List<Dependency> flatten(List<DependencyNode> dependencies)
	{
		for (DependencyNode dependency : dependencies)
			if (this.flattener.traverse(dependency) == VisitSignal.STOP)
				return Collections.emptyList();
				//TODO: Throw error and completely stop evaluation

		return this.flattener.finish();
	}

	public static class Builder
	{
		private DependencyTreeFlattener flattener;

		private Builder() {}

		public Resolver build()
		{
			return new Resolver(this.flattener);
		}

		public Builder flattener(DependencyTreeFlattener flattener)
		{
			this.flattener = flattener;
			return this;
		}
	}
}
