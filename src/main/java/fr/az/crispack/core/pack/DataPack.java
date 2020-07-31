package fr.az.crispack.core.pack;

import java.util.List;

import fr.az.crispack.core.dependency.Dependency;
import fr.az.crispack.core.version.Version;

public record DataPack(PackIdentity identity, List<Dependency> dependencies) implements Pack
{
	public DataPack
	{
		if (identity.type() != PackType.DATAPACK)
			throw new IllegalArgumentException("A datapack identity may only have datapack type.");
	}

	@Override public String author() { return this.identity.author(); }
	@Override public String name() { return this.identity.name(); }
	@Override public Version version() { return this.identity.version(); }
}