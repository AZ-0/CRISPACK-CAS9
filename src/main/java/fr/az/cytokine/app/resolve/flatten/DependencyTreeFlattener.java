package fr.az.cytokine.app.resolve.flatten;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.app.dependency.DependencyNode;
import fr.az.cytokine.app.dependency.VersionedDependency;
import fr.az.cytokine.app.resolve.conflict.ConflictHandler;
import fr.az.cytokine.app.resolve.conflict.ConflictHandlingStrategy;
import fr.az.cytokine.app.resolve.conflict.VersionConflictException;
import fr.az.cytokine.util.trees.visit.TreeVisitor;
import fr.az.cytokine.util.trees.visit.VisitSignal;

public class DependencyTreeFlattener implements TreeVisitor<DependencyNode>
{
	public static DependencyTreeFlattener of() { return builder().build(); }
	public static Builder builder() { return new Builder(); }

	private final List<Dependency> dependencies;
	private final Set<FlatDependency> versioned;

	private final VisitSignal signalOnConflict;
	private ConflictHandler conflictHandler;

	private DependencyTreeFlattener(ConflictHandler conflictHandler, VisitSignal signalOnConflict)
	{
		this.dependencies = new ArrayList<>();
		this.versioned = new HashSet<>();

		this.signalOnConflict = signalOnConflict;
		this.conflictHandler = conflictHandler;
	}

	@Override
	public VisitSignal visit(DependencyNode node, int depth)
	{
		if (!node.identity().dependency().hasVersion())
		{
			this.dependencies.add(node.identity().dependency());
			return VisitSignal.CONTINUE;
		}

		VersionedDependency candidate = node.identity().dependency().withVersion();
		FlatDependency concurrent = new FlatDependency(candidate, depth);
		FlatDependency registered = null;

		Iterator<FlatDependency> iterator = this.versioned.iterator();

		while (iterator.hasNext())
		{
			FlatDependency next = iterator.next();

			if (next.dependency().isSimilar(candidate) && !next.dependency().hasSameVersion(candidate))
			{
				iterator.remove();
				registered = next;
				break;
			}
		}

		if (registered != null)
			return this.handleConflict(registered, concurrent);

		this.versioned.add(concurrent);
		return VisitSignal.CONTINUE;
	}

	private VisitSignal handleConflict(FlatDependency registered, FlatDependency concurrent)
	{
		FlatDependency chosen;

		try
		{
			chosen = this.conflictHandler.solve(registered, concurrent);
		} catch (VersionConflictException e)
		{
			return this.signalOnConflict;
		}

		this.versioned.add(chosen);

		return VisitSignal.CONTINUE;
	}

	public List<Dependency> finish()
	{
		this.versioned.forEach(flat -> this.dependencies.add(flat.dependency()));
		this.versioned.clear();
		return this.dependencies;
	}

	public List<Dependency> getCurrentDependencies() { return this.dependencies; }
	public void setConflictHandler(ConflictHandler handler) { this.conflictHandler = handler; }

	public static class Builder
	{
		public static final VisitSignal DEFAULT_SIGNAL = VisitSignal.STOP;

		private ConflictHandler conflictHandler;
		private VisitSignal signalOnConflict;

		public DependencyTreeFlattener build()
		{
			if (this.conflictHandler  == null) this.conflictHandler(ConflictHandlingStrategy.DEFAULT);
			if (this.signalOnConflict == null) this.signalOnConflict(DEFAULT_SIGNAL);

			return new DependencyTreeFlattener(this.conflictHandler, this.signalOnConflict);
		}

		public Builder conflictHandler(ConflictHandlingStrategy strategy)
		{
			return this.conflictHandler(ConflictHandler.of(strategy));
		}

		public Builder conflictHandler(ConflictHandler handler)
		{
			this.conflictHandler = handler;
			return this;
		}

		public Builder signalOnConflict(VisitSignal signal)
		{
			this.signalOnConflict = signal;
			return this;
		}
	}
}
