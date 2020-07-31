package fr.az.crispack.core.load;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import fr.az.crispack.App;
import fr.az.crispack.core.Save;
import fr.az.crispack.core.pack.DataPack;
import fr.az.crispack.core.pack.ResourcesPack;
import fr.az.crispack.property.Properties;
import fr.az.crispack.util.Util;

public class PackLoader
{
	private static final LoadingFactory LOADING_FACTORY = new LoadingFactory();

	private final Map<String, Save> saves;
	private final Map<String, DataPack> datapacks;
	private final Map<String, ResourcesPack> resourcesPacks;

	public PackLoader()
	{
		this.saves = new HashMap<>();
		this.datapacks = new HashMap<>();
		this.resourcesPacks = new HashMap<>();
	}

	public void reload()
	{
		this.saves.clear();
		this.datapacks.clear();
		this.resourcesPacks.clear();

		Properties.getSaves()
				  .filter(Util::existsDir)
				  .forEach(path -> this.register(this.loadSave(path, true)));
	}

	public Optional<Save> loadSave(String name, boolean register)
	{
		Path saveFolder = Properties.getSaveFolder(name);

		if (Util.existsDir(saveFolder))
			return Optional.of(this.loadSave(saveFolder, register));

		return Optional.empty();
	}

	public Optional<DataPack> loadDataPack(String name, boolean register)
	{
		Set<Path> candidates = new HashSet<>();

		Properties.getDatapacks()
				  .filter(path -> path.getFileName().toString().equals(name))
				  .forEach(candidates::add);

		if (candidates.isEmpty())
		{
			App.logger().warning("Could not find any datapack with name '%s'".formatted(name));
			return Optional.empty();
		}

		App.logger().info("Found %s datapack(s) matching '%s'".formatted(candidates.size(), name));

		for (Path candidate : candidates)
		{
			String saveName = candidate.getParent().getParent().getFileName().toString();
			Optional<DataPack> opt = this.loadDataPack(saveName, candidate);

			this.registerDataPack(opt, this.datapacks);
			if (opt.isPresent())
				return opt;
		}

		return Optional.empty();
	}

	public Save loadSave(Path save, boolean register)
	{
		String name = save.getFileName().toString();
		Path datapacksFolder = Properties.getDatapackFolder(save);
		Map<String, DataPack> datapacks = new HashMap<>();

		App.logger().info("++++++++++ Loading map '%s' ++++++++++".formatted(name));

		if (!Util.existsDir(datapacksFolder))
			return this.finishLoadSave(name, datapacks, save, register);

		Util.list(datapacksFolder)
			.map(dp -> this.loadDataPack(name, dp))
			.forEach(dp -> this.registerDataPack(dp, datapacks));

		return this.finishLoadSave(name, datapacks, save, register);
	}

	private void registerDataPack(Optional<DataPack> pack, Map<String, DataPack> registry)
	{
		if (pack.isPresent())
		{
			DataPack dp = pack.get();
			registry.put(dp.name(), dp);
		}
	}

	private Save finishLoadSave(String name, Map<String, DataPack> datapacks, Path dir, boolean register)
	{
		Save save = new Save(name, datapacks, dir);

		if (register)
			this.register(save);

		return save;
	}

	public Optional<DataPack> loadDataPack(String save, Path path)
	{
		return LOADING_FACTORY.getDatapackLoader(path).load(save);
	}

	private void register(Save save)
	{
		this.saves.put(save.name(), save);

		for (DataPack dp : save.datapacks().values())
			this.datapacks.put(dp.name(), dp);
	}

	public Map<String, Save> saves() { return this.saves; }
	public Map<String, DataPack> datapacks() { return this.datapacks; }
	public Map<String, ResourcesPack> resourcesPacks() { return this.resourcesPacks; }
}
