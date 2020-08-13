package fr.az.cytokine.api.core.resolve.flatten;

import fr.az.cytokine.api.core.dependency.Dependency;
import fr.az.cytokine.api.core.dependency.DependencyVisitor;
import fr.az.cytokine.api.core.dependency.DirectDependency;
import fr.az.cytokine.api.core.dependency.GithubDependency;
import fr.az.cytokine.api.core.dependency.visitors.MapperDependencyVisitor;

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
		return this.first.accept(this);
	}

	@Override
	public Boolean visit(GithubDependency dependency)
	{
		MapperDependencyVisitor<Boolean> mapper = new MapperDependencyVisitor<>
		(
			github -> checkConflict(dependency, github),
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
