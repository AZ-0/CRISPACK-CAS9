package fr.az.cytokine.app.resolve;

import fr.az.cytokine.app.dependency.Dependency;

import reactor.core.publisher.Flux;

public interface DependencyCollector
{
	Flux<Dependency> collect(Dependency mother);
}
