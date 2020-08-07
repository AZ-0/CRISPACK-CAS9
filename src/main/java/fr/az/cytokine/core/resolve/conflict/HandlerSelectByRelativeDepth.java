package fr.az.cytokine.core.resolve.conflict;

import fr.az.cytokine.App;
import fr.az.cytokine.core.resolve.flatten.FlatDependency;

public class HandlerSelectByRelativeDepth implements ConflictHandler
{
	private final boolean chooseDeepest;

	public HandlerSelectByRelativeDepth(boolean chooseDeepest)
	{
		this.chooseDeepest = chooseDeepest;
	}

	@Override
	public FlatDependency solve(FlatDependency registered, FlatDependency concurrent) throws VersionConflictException
	{
		int relativeDepth = registered.compareTo(concurrent);

		if		(relativeDepth < 0) return this.chooseDeepest ? registered : concurrent;
		else if (relativeDepth > 0) return this.chooseDeepest ? concurrent : registered;
		else
		{
			App.logger().warning("Found both dependencies at same depth, resorting to default solver.");
			return ConflictHandler.getDefault().solve(registered, concurrent);
		}
	}
}