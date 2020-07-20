package fr.az.registry.core.load.datapack;

import java.util.Optional;

import fr.az.registry.App;
import fr.az.registry.core.load.PackLoadingException;
import fr.az.registry.core.pack.DataPack;

public interface DatapackLoader
{
	default Optional<DataPack> loadDataPack(String save)
	{
		Optional<DataPack> loaded;

		try
		{
			loaded = this.loadDataPackUnhandled(save);
			loaded.ifPresentOrElse
			(
				dp -> App.logger().info("Successfully loaded %s [%s]".formatted(dp.name(), save)),
				() -> {}
			);
		} catch (PackLoadingException e)
		{
			loaded = Optional.empty();
			App.logger().warning(e.getMessage());
		}

		return loaded;
	}

	public Optional<DataPack> loadDataPackUnhandled(String save) throws PackLoadingException;
}
