package fr.az.cytokine.api.core.resolve.flatten.conflict;

import fr.az.cytokine.api.core.resolve.flatten.FlatDependency;

class HandlerThrowException implements ConflictHandler
{
	@Override
	public FlatDependency solve(FlatDependency registered, FlatDependency concurrent) throws VersionConflictException
	{
		throw new VersionConflictException(registered, concurrent, "Unresolved version conflict!");
	}
}