package fr.cytokine.api.core.resolve.flatten;

import fr.cytokine.api.core.resolve.flatten.conflict.ConflictHandler;
import fr.cytokine.api.core.resolve.flatten.conflict.ConflictHandlingStrategy;
import fr.cytokine.api.core.resolve.flatten.conflict.VersionConflictException;
import fr.cytokine.api.dependency.Dependency;
import fr.cytokine.api.dependency.VersionedDependency;
import fr.cytokine.api.dependency.tree.DependencyNode;
import fr.cytokine.api.dependency.tree.DependencyTreeVisitor;

import java.util.*;

public class DependencyTreeFlattener implements DependencyTreeVisitor<VersionConflictException>
{
	private final List<Dependency> dependencies;
	private final Set<FlatDependency> versioned;

	private ConflictHandler conflictHandler;

	public DependencyTreeFlattener(ConflictHandlingStrategy strategy)
	{
		this(ConflictHandler.of(strategy));
	}

	public DependencyTreeFlattener(ConflictHandler conflictHandler)
	{
		this.dependencies = new ArrayList<>();
		this.versioned = new HashSet<>();

		this.conflictHandler = Objects.requireNonNull(conflictHandler);
	}

	@Override
	public void visit(DependencyNode node, int depth) throws VersionConflictException
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

	private void handleConflict(FlatDependency registered, FlatDependency concurrent) throws VersionConflictException
	{
		FlatDependency chosen = this.conflictHandler.solve(registered, concurrent);
		this.versioned.add(chosen);
	}

	public List<Dependency> getFlattened()
	{
		this.versioned.forEach(flat -> this.dependencies.add(flat.dependency()));
		this.versioned.clear();
		return this.dependencies;
	}

	public void setConflictHandler(ConflictHandler handler) { this.conflictHandler = handler; }
}
