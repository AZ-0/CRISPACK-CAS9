package fr.az.cytokine.infra.server.load;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import fr.az.cytokine.api.core.pack.DataPack;
import fr.az.cytokine.api.core.pack.Save;
import fr.az.cytokine.infra.server.load.datapack.DatapackLoader;
import fr.az.cytokine.infra.server.pack.SaveImpl;
import fr.az.cytokine.infra.server.property.Properties;

public class PackLoader
{
	private final Map<String, Save> saves;
	private final Map<String, DataPack> datapacks;

	public PackLoader()
	{
		this.saves = new HashMap<>();
		this.datapacks = new HashMap<>();
	}

	public void reload()
	{
		this.saves.clear();
		this.datapacks.clear();

/*		Properties.getSaves()
				  .filter(Util::existsDir)
				  .forEach(path -> this.register(this.loadSave(path, true)));
*/	}

	public Optional<Save> loadSave(String name, boolean register)
	{
		Path saveFolder = Properties.getSaveFolder(name);

		if (Files.isDirectory(saveFolder))
			return Optional.of(this.loadSave(saveFolder, register));

		return Optional.empty();
	}

	public Optional<DataPack> loadDataPack(String name, boolean register)
	{
		Set<Path> candidates = new HashSet<>();

//		Properties.getDatapacks()
//				  .filter(path -> path.getFileName().toString().equals(name))
//				  .forEach(candidates::add);

		if (candidates.isEmpty())
//			App.logger().warning("Could not find any datapack with name '%s'".formatted(name));
			return Optional.empty();

//		App.logger().info("Found %s datapack(s) matching '%s'".formatted(candidates.size(), name));

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

//		App.logger().info("++++++++++ Loading map '%s' ++++++++++".formatted(name));

		if (!Files.isDirectory(datapacksFolder))
			return this.finishLoadSave(name, datapacks, save, register);

//		Util.list(datapacksFolder)
//			.map(dp -> this.loadDataPack(name, dp))
//			.forEach(dp -> this.registerDataPack(dp, datapacks));

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
		Save save = new SaveImpl(name, datapacks);

		if (register)
			this.register(save);

		return save;
	}

	public Optional<DataPack> loadDataPack(String save, Path path)
	{
		return DatapackLoader.of(path).load(save);
	}

	private void register(Save save)
	{
		this.saves.put(save.name(), save);

		for (DataPack dp : save.packs().values())
			this.datapacks.put(dp.name(), dp);
	}

	public Map<String, Save> saves() { return this.saves; }
	public Map<String, DataPack> datapacks() { return this.datapacks; }
}
