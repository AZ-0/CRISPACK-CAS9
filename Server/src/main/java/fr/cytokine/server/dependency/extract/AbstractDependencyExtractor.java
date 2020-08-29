package fr.cytokine.server.dependency.extract;

import java.util.Objects;

import fr.az.cytokine.domain.dependency.extract.DependencyExtractor;
import fr.cytokine.server.dependency.extract.context.ReadingContext;

abstract class AbstractDependencyExtractor implements DependencyExtractor
{
	private final ReadingContext context;

	AbstractDependencyExtractor(ReadingContext context)
	{
		this.context = Objects.requireNonNull(context);
	}

	public ReadingContext context() { return this.context; }
}
