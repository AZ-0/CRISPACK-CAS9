package fr.az.crispack.core.resolve.conflict;

import java.util.function.Supplier;

public class ConflictHandlerFactory implements Supplier<ConflictHandler>
{
	private final ConflictHandlingStrategy strategy;

	public ConflictHandlerFactory()
	{
		this(ConflictHandlingStrategy.DEFAULT);
	}

	public ConflictHandlerFactory(ConflictHandlingStrategy strategy)
	{
		this.strategy = strategy == null ? ConflictHandlingStrategy.DEFAULT : strategy;
	}

	public ConflictHandler get()
	{
		return switch (this.strategy)
		{
			case RETAIN_MIN_DEPTH		-> new HandlerSelectByRelativeDepth(false);
			case RETAIN_MAX_DEPTH		-> new HandlerSelectByRelativeDepth(true);
			case RETAIN_FIRST_DECLARED	-> new HandlerSelectByDeclaration(true);
			case RETAIN_LAST_DECLARED	-> new HandlerSelectByDeclaration(false);
			case THROW_ERROR			-> new HandlerThrowException();
		};
	}
}
