package fr.az.crispack.core.resolve;

import java.util.List;

import fr.az.crispack.core.resolve.flatten.DependencyTreeFlattener;
import fr.az.crispack.core.resolve.flatten.FlatDependency;
import fr.az.crispack.util.trees.visit.VisitSignal;

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
			.flatMapSequential(Resolver::createTree)
			.buffer()
			.flatMap(this::flatten)
			//TODO: Resolve gathered dependencies
			;
	}

	public static Mono<Dependency> createTree(Dependency dependency) { return buildTree(dependency).then(Mono.just(dependency)); }
	private static Flux<Dependency> buildTree(Dependency dependency)
	{
		return dependency.collect().flatMap(Resolver::createTree);
	}

	private Flux<FlatDependency> flatten(List<Dependency> dependencies)
	{
		for (Dependency dependency : dependencies)
			if (this.flattener.traverse(dependency) == VisitSignal.STOP)
				return Flux.empty();

		return Flux.fromIterable(this.flattener.getDependencies());
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
