package fr.cytokine.api.core.resolve.flatten;

import fr.cytokine.api.dependency.Dependency;
import fr.cytokine.api.dependency.DependencyVisitor;
import fr.cytokine.api.dependency.DirectDependency;
import fr.cytokine.api.dependency.GithubDependency;
import fr.cytokine.api.dependency.visitors.MapperDependencyVisitor;

public class DependencyConflictChecker implements DependencyVisitor<Boolean>
{
	public static boolean conflict(GithubDependency first, GithubDependency second)
	{
		return first.author().equals(second.author())
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
		return this.first.accept(this);
	}

	@Override
	public Boolean visit(GithubDependency dependency)
	{
		MapperDependencyVisitor<Boolean> mapper = new MapperDependencyVisitor<>
		(
			github -> conflict(dependency, github),
			direct -> false
		);

		return this.second.accept(mapper);
	}

	@Override
	public Boolean visit(DirectDependency dependency)
	{
		return false;
	}
}
