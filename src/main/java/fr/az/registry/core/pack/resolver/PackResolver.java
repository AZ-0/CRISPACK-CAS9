package fr.az.registry.core.pack.resolver;

import fr.az.registry.core.pack.content.PackContent;

import reactor.core.publisher.Mono;

public interface PackResolver
{
	Mono<PackContent> resolve();
}
