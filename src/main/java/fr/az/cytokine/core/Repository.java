package fr.az.cytokine.core;

import fr.az.cytokine.core.dependency.Dependency;

import reactor.core.publisher.Flux;

public interface Repository
{
	Flux<Dependency> collect(Dependency source);
}
