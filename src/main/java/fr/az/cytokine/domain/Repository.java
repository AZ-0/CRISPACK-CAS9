package fr.az.cytokine.domain;

import fr.az.cytokine.domain.dependency.Dependency;

import reactor.core.publisher.Flux;

public interface Repository
{
	Flux<Dependency> collect(Dependency source);
}
