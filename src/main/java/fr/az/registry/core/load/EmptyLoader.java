package fr.az.registry.core.load;

import java.util.Optional;

import fr.az.registry.core.load.datapack.DatapackLoader;
import fr.az.registry.core.pack.DataPack;

public class EmptyLoader implements DatapackLoader
{
	@Override
	public Optional<DataPack> loadDataPackUnhandled(String save) throws PackLoadingException
	{
		throw new PackLoadingException("The datapack doesn't exist!");
	}
}
