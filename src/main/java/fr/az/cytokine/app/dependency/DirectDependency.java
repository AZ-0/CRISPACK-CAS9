package fr.az.cytokine.app.dependency;

import java.nio.file.Path;

public interface DirectDependency extends Dependency
{
	Path path();

	@Override
	default <T> T visit(DependencyVisitor<T> visitor)
	{
		return visitor.visit(this);
	}
}
