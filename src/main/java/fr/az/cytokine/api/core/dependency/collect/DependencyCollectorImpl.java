package fr.az.cytokine.api.core.dependency.collect;

import fr.az.cytokine.api.core.dependency.Dependency;
import fr.az.cytokine.api.core.dependency.DependencyVisitor;
import fr.az.cytokine.api.core.dependency.DirectDependency;
import fr.az.cytokine.api.core.dependency.GithubDependency;
import fr.az.cytokine.domain.GithubDao;

import reactor.core.publisher.Flux;

public class DependencyCollectorImpl implements DependencyCollector, DependencyVisitor<Flux<Dependency>>
{
	private final GithubDao githubDao;

	public DependencyCollectorImpl(GithubDao githubDao)
	{
		this.githubDao = githubDao;
	}

	@Override
	public Flux<Dependency> collect(Dependency mother)
	{
		return mother.accept(this);
	}

	@Override
	public Flux<Dependency> visit(GithubDependency dependency)
	{
		return this.githubDao.collectDependencies(dependency);
	}

	@Override
	public Flux<Dependency> visit(DirectDependency dependency)
	{
		return null;
	}
}