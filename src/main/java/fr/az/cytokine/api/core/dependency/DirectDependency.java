package fr.az.cytokine.api.core.dependency;

import java.nio.file.Path;

public interface DirectDependency extends Dependency
{
	Path path();

	@Override
	default <T> T accept(DependencyVisitor<T> visitor)
	{
		return visitor.visit(this);
	}
}
