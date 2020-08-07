package fr.az.cytokine.json.dependency.read;

import org.json.JSONObject;

import fr.az.cytokine.core.dependency.Dependency;
import fr.az.cytokine.core.dependency.context.ReadingContext;
import fr.az.util.parsing.json.keys.types.ObjectArrayKey;

public abstract class ObjectDependencyKey extends ObjectArrayKey<Dependency> implements DependencyKey<JSONObject>
{
	private static final long serialVersionUID = -7247133910362922224L;

	private final ReadingContext context;

	public ObjectDependencyKey(ReadingContext context)
	{
		this.context = context;
	}

	@Override public String getKey() { return DependencyKey.super.getKey(); }
	@Override public ReadingContext context() { return this.context; }
}
