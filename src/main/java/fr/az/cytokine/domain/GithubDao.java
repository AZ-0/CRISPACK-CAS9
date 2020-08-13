package fr.az.cytokine.domain;

import fr.az.cytokine.api.core.dependency.Dependency;
import fr.az.cytokine.api.core.dependency.GithubDependency;

import reactor.core.publisher.Flux;

public interface GithubDao
{
	Flux<Dependency> collectDependencies(GithubDependency mother);
}
