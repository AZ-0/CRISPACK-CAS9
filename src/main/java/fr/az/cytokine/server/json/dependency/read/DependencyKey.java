package fr.az.cytokine.server.json.dependency.read;

import fr.az.cytokine.domain.dependency.Dependency;
import fr.az.cytokine.server.dependency.context.ReadingContext;
import fr.az.util.parsing.json.keys.types.ArrayKey;

public interface DependencyKey<T> extends ArrayKey<T, Dependency>
{
	static DependencyKey<?>[] getKeys(ReadingContext context)
	{
		return new DependencyKey<?>[]
		{
			new KeyGithubDependency(context)
		};
	}

	ReadingContext context();
}