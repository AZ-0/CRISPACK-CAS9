package fr.az.crispack.core.load.datapack;

import java.util.Optional;

import fr.az.crispack.App;
import fr.az.crispack.core.load.PackLoadingException;
import fr.az.crispack.core.pack.DataPack;

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
