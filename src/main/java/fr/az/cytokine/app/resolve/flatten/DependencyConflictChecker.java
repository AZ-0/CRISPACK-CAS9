package fr.az.cytokine.app.resolve.flatten;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.app.dependency.DependencyVisitor;
import fr.az.cytokine.app.dependency.DirectDependency;
import fr.az.cytokine.app.dependency.GithubDependency;
import fr.az.cytokine.app.dependency.visitors.MapperDependencyVisitor;

public class DependencyConflictChecker implements DependencyVisitor<Boolean>
{
	public static boolean checkConflict(GithubDependency first, GithubDependency second)
	{
		return first.type().equals(second.type())
			&& first.author().equals(second.author())
			&& first.name().equals(second.name())
			&& !first.version().equals(second.version());
	}

	private final Dependency first;
	private final Dependency second;

	public DependencyConflictChecker(Dependency first, Dependency second)
	{
		this.first = first;
		this.second = second;
	}

	public boolean hasConflict()
	{
		return this.first.visit(this);
	}

	@Override
	public Boolean visit(GithubDependency dependency)
	{
		MapperDependencyVisitor<Boolean> mapper = new MapperDependencyVisitor<>
		(
			github -> checkConflict(dependency, github),
			direct -> false
		);

		return this.second.visit(mapper);
	}

	@Override
	public Boolean visit(DirectDependency dependency)
	{
		return false;
	}
}
