package fr.az.cytokine.app.dependency;

public interface DependencyVisitor<T>
{
	T visit(GithubDependency dependency);
	T visit(DirectDependency dependency);
}
