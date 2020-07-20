package fr.az.crispack.command;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import fr.az.crispack.App;
import fr.az.crispack.core.Save;
import fr.az.crispack.core.load.PackLoader;
import fr.az.crispack.core.pack.DataPack;

@Parameters(commandDescription = "Load and reload maps or datapacks")
public class LoadCommand extends Commands
{
	public static final PackLoader LOADER = new PackLoader();

	@Parameter(names = {"-r", "--reload"}, description = "reload already loaded and specified maps or datapacks")
	boolean reload;

	@Parameter(names = {"-a", "-all"}, description = "reload all maps and datapacks")
	boolean loadAll;

	protected LoadCommand()
	{
		super("load");
	}

	@Override
	public void reset()
	{
		this.reload = false;
		this.loadAll = false;
	}

	@Override
	public boolean execute()
	{
		if (this.loadAll)
			LOADER.reload();

		return false;
	}

	public Optional<Save>	  loadSave		(String name) { return this.load("map",		 name, LOADER.saves(),	   LOADER::loadSave);	  }
	public Optional<DataPack> loadDataPack	(String name) { return this.load("datapack", name, LOADER.datapacks(), LOADER::loadDataPack); }

	private <T> Optional<T> load(String kind, String name, Map<String, T> pool, BiFunction<String, Boolean, Optional<T>> loader)
	{
		T value;

		if (this.reload || (value = pool.get(name)) == null)
		{
			Optional<T> loaded = loader.apply(name, true);

			if (loaded.isEmpty())
			{
				App.logger().warning("Could not find %s for name '%s'".formatted(kind, name));
				return loaded;
			}

			return loaded;
		}

		App.logger().info("The %s '%s' is already loaded".formatted(kind, name));
		return Optional.of(value);
	}
}
