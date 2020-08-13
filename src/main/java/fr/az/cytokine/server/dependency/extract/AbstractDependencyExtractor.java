package fr.az.cytokine.server.dependency.extract;

import java.util.Objects;

import fr.az.cytokine.server.dependency.context.ReadingContext;

public abstract class AbstractDependencyExtractor implements fr.az.cytokine.domain.dependency.extract.DependencyExtractor
{
	private final ReadingContext context;

	public AbstractDependencyExtractor(ReadingContext context)
	{
		this.context = Objects.requireNonNull(context, "can only extract with non-null reading context");
	}

	public ReadingContext context() { return this.context; }
}
