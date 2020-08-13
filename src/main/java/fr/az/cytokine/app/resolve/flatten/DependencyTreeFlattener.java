package fr.az.cytokine.app.resolve.flatten;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.app.dependency.VersionedDependency;
import fr.az.cytokine.app.dependency.tree.DependencyNode;
import fr.az.cytokine.app.dependency.tree.DependencyTreeVisitor;
import fr.az.cytokine.app.resolve.flatten.conflict.ConflictHandler;
import fr.az.cytokine.app.resolve.flatten.conflict.ConflictHandlingStrategy;
import fr.az.cytokine.app.resolve.flatten.conflict.VersionConflictException;

public class DependencyTreeFlattener implements DependencyTreeVisitor
{
	public static DependencyTreeFlattener of() { return builder().build(); }
	public static Builder builder() { return new Builder(); }

	private final List<Dependency> dependencies;
	private final Set<FlatDependency> versioned;

	private ConflictHandler conflictHandler;

	private DependencyTreeFlattener(ConflictHandler conflictHandler)
	{
		this.dependencies = new ArrayList<>();
		this.versioned = new HashSet<>();

		this.conflictHandler = conflictHandler;
	}

	@Override
	public void visit(DependencyNode node, int depth)
	{
		if (!node.dependency().hasVersion())
		{
			this.dependencies.add(node.dependency());
			return;
		}

		VersionedDependency candidate = node.dependency().withVersion();

		FlatDependency concurrent = new FlatDependency(candidate, depth);
		FlatDependency registered = null;

		Iterator<FlatDependency> iterator = this.versioned.iterator();

		while (iterator.hasNext())
		{
			FlatDependency next = iterator.next();

			if (next.dependency().equals(candidate))
			{
				if (depth < next.depth())
				{
					iterator.remove();
					this.versioned.add(concurrent);
				}

				break;
			}

			DependencyConflictChecker conflictChecker = new DependencyConflictChecker(candidate, next.dependency());

			if (conflictChecker.hasConflict())
			{
				iterator.remove();
				registered = next;
				break;
			}
		}

		if (registered != null)
			this.handleConflict(registered, concurrent);
		else
			this.versioned.add(concurrent);
	}

	private void handleConflict(FlatDependency registered, FlatDependency concurrent)
	{
		FlatDependency chosen;

		try
		{
			chosen = this.conflictHandler.solve(registered, concurrent);
		} catch (VersionConflictException e)
		{
			e.printStackTrace();
			return;
		}

		this.versioned.add(chosen);
	}

	public List<Dependency> getFlattened()
	{
		this.versioned.forEach(flat -> this.dependencies.add(flat.dependency()));
		this.versioned.clear();
		return this.dependencies;
	}

	public void setConflictHandler(ConflictHandler handler) { this.conflictHandler = handler; }


	public static class Builder
	{
		private ConflictHandler conflictHandler;

		public Builder()
		{
			this.conflictHandler(ConflictHandlingStrategy.DEFAULT);
		}

		public DependencyTreeFlattener build()
		{
			return new DependencyTreeFlattener(this.conflictHandler);
		}


		public Builder conflictHandler(ConflictHandlingStrategy strategy) {
			return this.conflictHandler(ConflictHandler.of(strategy)); }

		public Builder conflictHandler(ConflictHandler handler)
		{
			this.conflictHandler = Objects.requireNonNull(handler);
			return this;
		}
	}
}
