package fr.az.cytokine.command;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

import fr.az.cytokine.App;
import fr.az.cytokine.command.delegate.SelectionArgs;
import fr.az.cytokine.core.Save;
import fr.az.cytokine.core.load.PackLoader;
import fr.az.cytokine.core.pack.DataPack;

@Parameters(commandDescription = "display datapack informations")
public class DisplayCommand extends Commands
{
	private static final PackLoader LOADER = LoadCommand.LOADER;

	@Parameter(names = { "-a", "-all" }, description = "display all available informations")
	private boolean displayAll;

	@ParametersDelegate
	private final SelectionArgs selection = new SelectionArgs();

	protected DisplayCommand()
	{
		super("info", "infos", "display", "disp");
	}

	@Override
	public void reset()
	{
		this.displayAll = false;
		this.selection.reset();
	}

	@Override
	public boolean execute()
	{
		if (this.displayAll)
		{
			this.allInfos();
			return true;
		}

		if (!this.selection.maps().isEmpty())
		{
			this.displaySaves(this.selection.maps());
			return true;
		}

		if (!this.selection.datapacks().isEmpty())
		{
			this.displayDatapacks(this.selection.datapacks());
			return true;
		}

		App.logger().info("Display more informations with -a, or select maps or datapacks with -m and -dp.\n");
		this.basicInfos();
		return true;
	}

	public void basicInfos()
	{
		App.logger().info
		(
			"Loaded maps: %s\nLoaded datapacks: %s".formatted
			(
				LOADER.saves().size(),
				LOADER.datapacks().size()
			)
		);
	}

	public void allInfos()
	{
		this.basicInfos();
		App.logger().info("");
		this.displaySaves(LOADER.saves().keySet());
		this.displayDatapacks(LOADER.datapacks().keySet());
	}

	public void displaySaves(Iterable<String> names)
	{
		Map<String, Save> saves = LOADER.saves();
		this.display("map", names, this::loadSave, saves::get, s -> this.displaySave(s, this.selection.datapacks()), saves::isEmpty);
	}

	public void displayDatapacks(Iterable<String> names)
	{
		Map<String, DataPack> dps = LOADER.datapacks();
		this.display("datapack", names, this::loadDataPack, dps::get, dp -> this.displayDataPack(dp, ""), dps::isEmpty);
	}

	public void displaySave(Save save, Collection<String> datapacks)
	{
		String description =
			"""
			%s: %s
				Data Packs [%s]:"""
			.formatted
			(
				save.name(),
				save.dir().toAbsolutePath(),
				save.datapacks().size()
			);

		for (DataPack dp : save.datapacks().values())
			if (datapacks.contains(dp.name()))
				this.displayDataPack(dp, "\t\t");

		App.logger().info(description);
	}

	public void displayDataPack(DataPack pack, String indent)
	{

	}

	private <T> void display
	(
		String						name,
		Iterable<String>			names,
		Function<String, Boolean>	loader,
		Function<String, T>			provider,
		Consumer<T>					displayer,
		Supplier<Boolean>			noValueChecker
	)
	{
		App.logger().info("< %sS >".formatted(name.toUpperCase()));

		for (String target : names)
		{
			if (!loader.apply(target))
				continue;

			T value = provider.apply(target);
			displayer.accept(value);
		}

		if (noValueChecker.get())
		{
			App.logger().info("No %s loaded".formatted(name));
			return;
		}
	}

	private boolean loadSave	(String map)	  { return this.load(map,		LOADER.saves(),		Commands.LOAD::loadSave);	  }
	private boolean loadDataPack(String datapack) { return this.load(datapack,	LOADER.datapacks(),	Commands.LOAD::loadDataPack); }

	private <T> boolean load(String name, Map<String, T> pool, Function<String, Optional<T>> loader)
	{
		if (!pool.containsKey(name))
			return loader.apply(name).isPresent();

		return true;
	}
}
