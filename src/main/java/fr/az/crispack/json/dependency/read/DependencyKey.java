package fr.az.crispack.json.dependency.read;

import fr.az.crispack.core.dependency.Dependency;
import fr.az.crispack.core.dependency.context.ReadingContext;
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

	Dependency.Kind kind();
	ReadingContext context();

	@Override public default String getKey() { return this.kind().key(); }
}