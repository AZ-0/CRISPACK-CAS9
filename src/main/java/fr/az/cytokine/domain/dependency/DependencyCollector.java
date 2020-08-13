package fr.az.cytokine.domain.dependency;

import fr.az.cytokine.app.dependency.Dependency;

import reactor.core.publisher.Flux;

public interface DependencyCollector
{
	Flux<Dependency> collect(Dependency mother);
}
