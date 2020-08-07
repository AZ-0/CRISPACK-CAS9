package fr.az.cytokine.server.load;

import java.nio.file.Files;
import java.nio.file.Path;

import fr.az.cytokine.server.load.datapack.DatapackLoader;
import fr.az.cytokine.server.load.datapack.DirectoryDatapackLoader;
import fr.az.cytokine.server.load.datapack.EmptyLoader;
import fr.az.cytokine.server.load.datapack.ZippedDatapackLoader;

public class LoadingFactory
{
	public DatapackLoader getDatapackLoader(Path datapack)
	{
		if (Files.isDirectory(datapack))
			return new DirectoryDatapackLoader(datapack);

		else if (Files.isRegularFile(datapack))
			return new ZippedDatapackLoader(datapack);

		else
			return new EmptyLoader();
	}
}
