package fr.az.cytokine.server.load.datapack;

import java.util.Optional;

import fr.az.cytokine.app.pack.DataPack;
import fr.az.cytokine.server.load.PackLoadingException;

public interface DatapackLoader
{
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
