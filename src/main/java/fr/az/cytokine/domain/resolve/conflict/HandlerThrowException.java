package fr.az.cytokine.domain.resolve.conflict;

import fr.az.cytokine.domain.resolve.flatten.FlatDependency;

public class HandlerThrowException implements ConflictHandler
{
	@Override
	public FlatDependency solve(FlatDependency registered, FlatDependency concurrent) throws VersionConflictException
	{
		throw new VersionConflictException(registered, concurrent, "Unresolved version conflict!");
	}
}
