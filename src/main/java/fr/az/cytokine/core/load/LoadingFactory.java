package fr.az.cytokine.core.load;

import java.nio.file.Path;

import fr.az.cytokine.core.load.datapack.DatapackLoader;
import fr.az.cytokine.core.load.datapack.DirectoryDatapackLoader;
import fr.az.cytokine.core.load.datapack.EmptyLoader;
import fr.az.cytokine.core.load.datapack.ZippedDatapackLoader;
import fr.az.cytokine.util.Util;

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
