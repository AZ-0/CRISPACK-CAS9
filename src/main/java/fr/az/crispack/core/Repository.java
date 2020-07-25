package fr.az.crispack.core;

import fr.az.crispack.core.resolve.Dependency;

import reactor.core.publisher.Flux;

public interface Repository
{
	Flux<Dependency> collect(Dependency source);
}
