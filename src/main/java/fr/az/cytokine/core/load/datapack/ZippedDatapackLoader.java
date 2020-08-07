package fr.az.cytokine.core.load.datapack;

import java.nio.file.Path;
import java.util.Optional;
import java.util.zip.ZipFile;

import fr.az.cytokine.core.pack.DataPack;
import fr.az.cytokine.util.Util;

public class ZippedDatapackLoader implements DatapackLoader
{
	private final ZipFile zip;

	public ZippedDatapackLoader(Path path)
	{
		this.zip = Util.safeOp(path.toFile(), ZipFile::new);
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
