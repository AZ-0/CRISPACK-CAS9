package fr.az.cytokine.infra.server.load.datapack;

import java.nio.file.Path;
import java.util.Optional;
import java.util.zip.ZipFile;

import fr.az.cytokine.app.pack.DataPack;

class ZippedDatapackLoader implements DatapackLoader
{
	private final ZipFile zip;

	public ZippedDatapackLoader(Path path)
	{
		this.zip = null;
	}

	@Override
	public Optional<DataPack> loadFrom(String save)
	{
		if (this.zip == null)
			return Optional.empty();

		//TODO Load zipped datapack
		return Optional.empty();
	}
}
