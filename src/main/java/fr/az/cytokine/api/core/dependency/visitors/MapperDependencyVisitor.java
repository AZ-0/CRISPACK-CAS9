package fr.az.cytokine.api.core.dependency.visitors;

import java.util.function.Function;

import fr.az.cytokine.api.core.dependency.DependencyVisitor;
import fr.az.cytokine.api.core.dependency.DirectDependency;
import fr.az.cytokine.api.core.dependency.GithubDependency;

public class MapperDependencyVisitor<T> implements DependencyVisitor<T>
{
	private final Function<GithubDependency, T> githubMapper;
	private final Function<DirectDependency, T> directMapper;

	public MapperDependencyVisitor(Function<GithubDependency, T> githubMapper, Function<DirectDependency, T> directMapper)
	{
		this.githubMapper = githubMapper;
		this.directMapper = directMapper;
	}

	@Override public T visit(GithubDependency dependency) { return this.githubMapper.apply(dependency); }
	@Override public T visit(DirectDependency dependency) { return this.directMapper.apply(dependency); }
}
