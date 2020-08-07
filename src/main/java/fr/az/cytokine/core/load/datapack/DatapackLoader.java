package fr.az.cytokine.core.load.datapack;

import java.util.Optional;

import fr.az.cytokine.App;
import fr.az.cytokine.core.load.PackLoadingException;
import fr.az.cytokine.core.pack.DataPack;

public interface DatapackLoader
{
	default Optional<DataPack> load(String save)
	{
		try
		{
			Optional<DataPack> loaded = this.loadFrom(save);
			loaded.ifPresent(dp -> App.logger().info("Successfully loaded %s [%s]".formatted(dp.name(), save)));
			return loaded;
		}
		catch (PackLoadingException e)
		{
			App.logger().error(e.getMessage());
			return Optional.empty();
		}
	}

	public Optional<DataPack> loadFrom(String save) throws PackLoadingException;
}
