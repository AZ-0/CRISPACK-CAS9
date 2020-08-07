package fr.az.cytokine.core.resolve.conflict;

import fr.az.cytokine.core.resolve.flatten.FlatDependency;

public class HandlerThrowException implements ConflictHandler
{
	@Override
	public FlatDependency solve(FlatDependency registered, FlatDependency concurrent) throws VersionConflictException
	{
		throw new VersionConflictException(registered, concurrent, "Unresolved version conflict!");
	}
}
