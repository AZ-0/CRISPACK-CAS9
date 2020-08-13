package fr.az.cytokine.infra.server.json.dependency.read;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.util.parsing.json.keys.types.ArrayKey;

public interface DependencyKey<T> extends ArrayKey<T, Dependency>
{
	static DependencyKey<?>[] getKeys()
	{
		return new DependencyKey<?>[]
		{
			new KeyGithubDependency()
		};
	}
}
