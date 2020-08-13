package fr.az.cytokine.infra.server.dependency.extract;

import java.util.Objects;

import fr.az.cytokine.domain.dependency.extract.DependencyExtractor;
import fr.az.cytokine.infra.server.dependency.extract.context.ReadingContext;

abstract class AbstractDependencyExtractor implements DependencyExtractor
{
	private final ReadingContext context;

	public AbstractDependencyExtractor(ReadingContext context)
	{
		this.context = Objects.requireNonNull(context);
	}

	public ReadingContext context() { return this.context; }
}
