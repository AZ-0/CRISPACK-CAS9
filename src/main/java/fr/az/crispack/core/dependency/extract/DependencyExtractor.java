package fr.az.crispack.core.dependency.extract;

import java.util.List;

import fr.az.crispack.core.dependency.Dependency;
import fr.az.crispack.core.dependency.context.ReadingContext;

public abstract class DependencyExtractor
{
	private final ReadingContext context;

	public DependencyExtractor(ReadingContext context)
	{
		this.context = context;
	}

	public ReadingContext context() { return this.context; }

	abstract List<Dependency> extract();
}
