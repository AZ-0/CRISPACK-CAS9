package fr.az.crispack.core.pack.resolver;

import fr.az.crispack.core.pack.content.PackContent;

import reactor.core.publisher.Mono;

public interface PackResolver
{
	Mono<PackContent> resolve();
}
