package fr.az.crispack.core.resolve.conflict;

import fr.az.crispack.core.resolve.flatten.FlatDependency;

public interface ConflictHandler
{
	FlatDependency solve(FlatDependency registered, FlatDependency concurrent) throws VersionConflictException;
}
