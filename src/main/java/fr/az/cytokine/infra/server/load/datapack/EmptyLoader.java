package fr.az.cytokine.infra.server.load.datapack;

import java.util.Optional;

import fr.az.cytokine.app.pack.DataPack;
import fr.az.cytokine.infra.server.load.PackLoadingException;

public class EmptyLoader implements DatapackLoader
{
	@Override
	public Optional<DataPack> loadFrom(String save) throws PackLoadingException
	{
		throw new PackLoadingException("The datapack doesn't exist!");
	}
}
