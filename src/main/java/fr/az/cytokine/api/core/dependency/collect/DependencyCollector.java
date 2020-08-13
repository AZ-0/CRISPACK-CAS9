package fr.az.cytokine.api.core.dependency.collect;

import fr.az.cytokine.api.core.dependency.Dependency;

import reactor.core.publisher.Flux;

public interface DependencyCollector
{
	Flux<Dependency> collect(Dependency mother);
}
