package fr.az.crispack.core.pack;

import java.util.Optional;

import fr.az.crispack.core.Save;
import fr.az.crispack.core.load.meta.McMeta;
import fr.az.crispack.core.pack.content.DataPackContent;
import fr.az.crispack.core.version.Version;

public record DataPack(McMeta meta, PackIdentity identity) implements Pack<DataPackContent>
{
	public DataPack
	{
		if (identity.type() != PackType.DATAPACK)
			throw new IllegalArgumentException("A datapack identity may only have datapack type.");
	}

	@Override
	public void writeContent(Save in, DataPackContent content)
	{
		//TODO write contents
	}

	@Override
	public Optional<DataPackContent> loadContent(Save in)
	{
		return Optional.empty();
	}

	@Override public String author() { return this.identity.author(); }
	@Override public String name() { return this.identity.name(); }
	@Override public Version version() { return this.identity.version(); }
}