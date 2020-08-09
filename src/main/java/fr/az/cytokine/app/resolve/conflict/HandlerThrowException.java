package fr.az.cytokine.app.resolve.conflict;

import fr.az.cytokine.app.resolve.flatten.FlatDependency;

public class HandlerThrowException implements ConflictHandler
{
	@Override
	public FlatDependency solve(FlatDependency registered, FlatDependency concurrent) throws VersionConflictException
	{
		throw new VersionConflictException(registered, concurrent, "Unresolved version conflict!");
	}
}
