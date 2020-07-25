package fr.az.crispack.core.resolve.conflict;

import fr.az.crispack.core.resolve.flatten.FlatDependency;

public class HandlerThrowException implements ConflictHandler
{
	@Override
	public FlatDependency solve(FlatDependency registered, FlatDependency concurrent) throws VersionConflictException
	{
		throw new VersionConflictException(registered, concurrent, "Unresolved version conflict!");
	}
}
