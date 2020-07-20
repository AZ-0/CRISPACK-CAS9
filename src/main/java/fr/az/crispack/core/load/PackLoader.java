package fr.az.crispack.core.load;

import java.io.File;
import java.nio.file.Files;
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

		Path savesFolder = Properties.savesFolder();
		for (File dir : savesFolder.toFile().listFiles(File::isDirectory))
			this.register(this.loadSave(dir.toPath(), true));
	}

	public Optional<Save> loadSave(String name, boolean register)
	{
		Path saveFolder = Properties.savesFolder().resolve(name);

		if (Files.isDirectory(saveFolder))
			return Optional.of(this.loadSave(saveFolder, register));

		return Optional.empty();
	}

	public Optional<DataPack> loadDataPack(String name, boolean register)
	{
		Path savesFolder = Properties.savesFolder();
		Set<Path> candidates = new HashSet<>();

		for (File save : savesFolder.toFile().listFiles(File::isDirectory))
		{
			save = new File(save, "datapacks");

			for (File candidate : save.listFiles((f, n) -> n.equals(name)))
				candidates.add(candidate.toPath());
		}

		if (candidates.isEmpty())
		{
			App.logger().info("Could not find datapack with name '%s'".formatted(name));
			return Optional.empty();
		}

		for (Path candidate : candidates)
		{
			String save = candidate.getParent().getParent().getFileName().toString();
			Optional<DataPack> datapack = this.loadDataPack(save, candidate);

			if (datapack.isPresent())
			{
				this.datapacks.put(datapack.get().name(), datapack.get());
				return datapack;
			}
		}

		return Optional.empty();
	}

	public Save loadSave(Path dir, boolean register)
	{
		String name = dir.getFileName().toString();
		Path datapacksFolder = dir.resolve("datapacks");
		Map<String, DataPack> datapacks = new HashMap<>();

		App.logger().info("} Loading map %s".formatted(name));

		if (Files.notExists(datapacksFolder) || !Files.isDirectory(datapacksFolder))
			return this.register(new Save(name, datapacks, dir), register);

		for (File file : datapacksFolder.toFile().listFiles())
			this.loadDataPack(name, file.toPath()).ifPresent(datapack -> datapacks.put(datapack.name(), datapack));

		return this.register(new Save(name, datapacks, dir), register);
	}

	public Optional<DataPack> loadDataPack(String save, Path path)
	{
		return LOADING_FACTORY.produceDatapackLoader(path).loadDataPack(save);
	}


	private Save register(Save save, boolean register)
	{
		if (register)
			this.register(save);

		return save;
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
