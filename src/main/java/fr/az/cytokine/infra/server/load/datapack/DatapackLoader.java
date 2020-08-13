package fr.az.cytokine.infra.server.load.datapack;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import fr.az.cytokine.app.pack.DataPack;
import fr.az.cytokine.infra.server.load.PackLoadingException;

public interface DatapackLoader
{
	static DatapackLoader of(Path pack)
	{
		if (Files.isDirectory(pack))
			return new DirectoryDatapackLoader(pack);

		else if (Files.isRegularFile(pack))
			return new ZippedDatapackLoader(pack);

		else
			return new EmptyLoader(pack);
	}

	default Optional<DataPack> load(String save)
	{
		try
		{
			Optional<DataPack> loaded = this.loadFrom(save);
//			loaded.ifPresent(dp -> App.logger().info("Successfully loaded %s [%s]".formatted(dp.name(), save)));
			return loaded;
		}
		catch (PackLoadingException e)
		{
			System.err.println(e.getMessage());
			return Optional.empty();
		}
	}

	public Optional<DataPack> loadFrom(String save) throws PackLoadingException;
}
