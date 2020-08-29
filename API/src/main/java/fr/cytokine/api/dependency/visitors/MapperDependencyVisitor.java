package fr.cytokine.api.dependency.visitors;

import fr.cytokine.api.dependency.DependencyVisitor;
import fr.cytokine.api.dependency.DirectDependency;
import fr.cytokine.api.dependency.GithubDependency;

import java.util.function.Function;

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
