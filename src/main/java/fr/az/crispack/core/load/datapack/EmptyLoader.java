package fr.az.crispack.core.load.datapack;

import java.util.Optional;

import fr.az.crispack.core.load.PackLoadingException;
import fr.az.crispack.core.pack.DataPack;

public class EmptyLoader implements DatapackLoader
{
	@Override
	public Optional<DataPack> loadFrom(String save) throws PackLoadingException
	{
		throw new PackLoadingException("The datapack doesn't exist!");
	}
}
