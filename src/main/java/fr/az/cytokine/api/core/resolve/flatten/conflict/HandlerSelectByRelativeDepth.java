package fr.az.cytokine.api.core.resolve.flatten.conflict;

import fr.az.cytokine.api.core.resolve.flatten.FlatDependency;

class HandlerSelectByRelativeDepth implements ConflictHandler
{
	private final boolean chooseDeepest;

	public HandlerSelectByRelativeDepth(boolean chooseDeepest)
	{
		this.chooseDeepest = chooseDeepest;
	}

	@Override
	public FlatDependency solve(FlatDependency registered, FlatDependency concurrent) throws VersionConflictException
	{
		int relativeDepth = Integer.compare(registered.depth(), concurrent.depth());

		if (relativeDepth < 0)
			return this.chooseDeepest ? registered : concurrent;
		else if (relativeDepth > 0)
			return this.chooseDeepest ? concurrent : registered;
		else
			return ConflictHandler.getDefault().solve(registered, concurrent);
	}
}
