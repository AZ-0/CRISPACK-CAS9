package fr.az.crispack.core.load;

import java.nio.file.Path;

import fr.az.crispack.core.load.datapack.DatapackLoader;
import fr.az.crispack.core.load.datapack.DirectoryDatapackLoader;
import fr.az.crispack.core.load.datapack.EmptyLoader;
import fr.az.crispack.core.load.datapack.ZippedDatapackLoader;
import fr.az.crispack.util.Util;

public class LoadingFactory
{
	public DatapackLoader getDatapackLoader(Path datapack)
	{
		if (Util.existsDir(datapack))
			return new DirectoryDatapackLoader(datapack);

		else if (Util.existsFile(datapack))
			return new ZippedDatapackLoader(datapack);

		else
			return new EmptyLoader();
	}
}
