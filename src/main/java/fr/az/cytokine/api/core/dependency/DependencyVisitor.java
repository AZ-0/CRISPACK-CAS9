package fr.az.cytokine.api.core.dependency;

public interface DependencyVisitor<T>
{
	T visit(GithubDependency dependency);
	T visit(DirectDependency dependency);
}
