package fr.az.crispack.core.load;

import java.nio.file.Files;
import java.nio.file.Path;

import fr.az.crispack.core.load.datapack.DatapackLoader;
import fr.az.crispack.core.load.datapack.DirectoryDatapackLoader;
import fr.az.crispack.core.load.datapack.ZippedDatapackLoader;

public class LoadingFactory
{
	public DatapackLoader getDatapackLoader(Path datapack)
	{
		if (Files.isDirectory(datapack))
			return new DirectoryDatapackLoader(datapack);
		else if (Files.isReadable(datapack))
			return new ZippedDatapackLoader(datapack);
		else
			return new EmptyLoader();
	}
}
