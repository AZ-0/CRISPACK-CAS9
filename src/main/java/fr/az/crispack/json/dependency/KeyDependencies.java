package fr.az.crispack.json.dependency;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.az.crispack.App;
import fr.az.crispack.core.dependency.Dependency;
import fr.az.crispack.core.dependency.context.ReadingContext;
import fr.az.crispack.json.dependency.read.DependencyKey;
import fr.az.util.parsing.json.JSONParsingException;
import fr.az.util.parsing.json.keys.structure.Optional;
import fr.az.util.parsing.json.keys.structure.Structure;
import fr.az.util.parsing.json.keys.types.RootKey;

public class KeyDependencies implements RootKey<List<Dependency>>
{
	private static final long serialVersionUID = -2336548736044746059L;

	private final Optional optional;

	public KeyDependencies(ReadingContext context)
	{
		this.optional = new Optional(DependencyKey.getKeys(context));
	}

	@Override
	public List<Dependency> build(List<Structure> structures) throws JSONParsingException
	{
		Map<?, List<Dependency>> collected = this.optional.cast();

		List<Dependency> accumulated = new ArrayList<>();
		collected.values().forEach(accumulated::addAll);
		return accumulated;
	}

	@Override public List<Structure> getStructures() { return List.of(this.optional); }

	@Override public String getKey() { return "dependencies"; }

	@Override
	public void onError(JSONParsingException e, String file)
	{
		App.logger().error("Couldn't load dependencies: "+ e.getMessage());
	}
}
