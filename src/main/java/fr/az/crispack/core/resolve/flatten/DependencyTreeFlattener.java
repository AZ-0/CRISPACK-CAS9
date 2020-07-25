package fr.az.crispack.core.resolve.flatten;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.az.crispack.App;
import fr.az.crispack.core.resolve.Dependency;
import fr.az.crispack.core.resolve.DependencyIdentity;
import fr.az.crispack.core.resolve.conflict.ConflictHandler;
import fr.az.crispack.core.resolve.conflict.ConflictHandlerFactory;
import fr.az.crispack.core.resolve.conflict.ConflictHandlingStrategy;
import fr.az.crispack.core.resolve.conflict.VersionConflictException;
import fr.az.crispack.util.trees.visit.TreeVisitor;
import fr.az.crispack.util.trees.visit.VisitSignal;

public class DependencyTreeFlattener implements TreeVisitor<Dependency, DependencyIdentity>
{
	public static DependencyTreeFlattener of() { return builder().build(); }
	public static Builder builder() { return new Builder(); }

	private final Map<DependencyIdentity, FlatDependency> dependencies;
	private final VisitSignal signalOnConflict;
	private ConflictHandler conflictHandler;

	private DependencyTreeFlattener(ConflictHandler conflictHandler, VisitSignal signalOnConflict)
	{
		this.dependencies		= new HashMap<>();
		this.signalOnConflict	= signalOnConflict;
		this.conflictHandler	= conflictHandler;
	}

	@Override
	public VisitSignal visit(Dependency node, int depth)
	{
		FlatDependency dependency = new FlatDependency(node, depth);
		FlatDependency registered = this.dependencies.putIfAbsent(node.getIdentity(), dependency);

		if (registered != null)
			return this.handleConflict(registered, dependency);

		return VisitSignal.CONTINUE;
	}

	private VisitSignal handleConflict(FlatDependency registered, FlatDependency concurrent)
	{
		App.logger().warning("Dependency conflict:\n  • %s\n  • %s\n".formatted(registered, concurrent));

		FlatDependency chosen;

		try
		{
			chosen = this.conflictHandler.solve(registered, concurrent);
		} catch (VersionConflictException e)
		{
			App.logger().error(String.format
			(
				"Could not resolve conflict, will %s dependency flattening",
				this.signalOnConflict.toString().toLowerCase()
			));
			return this.signalOnConflict;
		}

		App.logger().warning("Resolved conflict with: "+ chosen);
		this.dependencies.put(chosen.identity(), chosen);

		return VisitSignal.CONTINUE;
	}

	public void setConflictHandler(ConflictHandler handler) { this.conflictHandler = handler; }

	public Collection<FlatDependency> getDependencies() { return this.dependencies.values(); }


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
			return this.conflictHandler(new ConflictHandlerFactory(strategy).get());
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
