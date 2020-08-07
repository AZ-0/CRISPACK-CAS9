package fr.az.cytokine.domain.resolve.conflict;

import fr.az.cytokine.domain.resolve.flatten.FlatDependency;

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
			return ConflictHandler.getDefault().solve(registered, concurrent);
	}
}
