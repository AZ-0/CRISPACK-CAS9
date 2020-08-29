package fr.cytokine.api.core.resolve.flatten.conflict;

import fr.cytokine.api.core.resolve.flatten.FlatDependency;

import java.util.Objects;

public interface ConflictHandler
{
	static ConflictHandler getDefault() { return of(ConflictHandlingStrategy.DEFAULT); }

	static ConflictHandler of(ConflictHandlingStrategy strategy)
	{
		return switch (Objects.requireNonNull(strategy))
		{
			case RETAIN_MIN_DEPTH		-> new HandlerSelectByRelativeDepth(false);
			case RETAIN_MAX_DEPTH		-> new HandlerSelectByRelativeDepth(true);
			case RETAIN_FIRST_DECLARED	-> new HandlerSelectByDeclaration(true);
			case RETAIN_LAST_DECLARED	-> new HandlerSelectByDeclaration(false);
			case THROW_ERROR			-> new HandlerThrowException();
		};
	}

	FlatDependency solve(FlatDependency registered, FlatDependency concurrent) throws VersionConflictException;
}
