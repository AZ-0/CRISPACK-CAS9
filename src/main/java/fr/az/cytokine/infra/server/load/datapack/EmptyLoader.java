package fr.az.cytokine.infra.server.load.datapack;

import java.nio.file.Path;
import java.util.Optional;

import fr.az.cytokine.api.core.pack.DataPack;
import fr.az.cytokine.infra.server.load.PackLoadingException;

class EmptyLoader implements DatapackLoader
{
	private final PackLoadingException exception;

	public EmptyLoader(Path path)
	{
		this.exception = new PackLoadingException("Couldn't find any datapack at '%s'".formatted(path));
	}

	@Override
	public Optional<DataPack> loadFrom(String save) throws PackLoadingException
	{
		throw this.exception;
	}
}
