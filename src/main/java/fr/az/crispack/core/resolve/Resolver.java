package fr.az.crispack.core.resolve;

import fr.az.crispack.core.resolve.flatten.DependencyTreeFlattener;

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
			.flatMapSequential(d -> this.createTree(d).then(Mono.just(d)))
			.map(this::flatten)
//			.buffer()
//			.doOnNext(list -> list.forEach(d -> Dependency.ROOTS.visit(this.flattener, d)))
//			TODO: synchronous flatMap to visit tree
			;
	}
	private Flux<Dependency> createTree(Dependency dependency)
	{
		return dependency.collect().flatMapSequential(this::createTree);
	}

	private Dependency flatten(Dependency dependency)
	{
		this.flattener.traverse(dependency);
		return dependency;
	}

	public DependencyTreeFlattener flattener() { return this.flattener; }


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
