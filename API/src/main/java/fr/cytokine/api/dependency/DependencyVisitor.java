package fr.cytokine.api.dependency;

public interface DependencyVisitor<T>
{
	T visit(GithubDependency dependency);
	T visit(DirectDependency dependency);
}
