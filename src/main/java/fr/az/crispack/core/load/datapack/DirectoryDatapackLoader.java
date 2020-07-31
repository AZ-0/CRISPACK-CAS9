package fr.az.crispack.core.load.datapack;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import fr.az.crispack.core.load.PackLoadingException;
import fr.az.crispack.core.load.meta.InvalidMetaException;
import fr.az.crispack.core.load.meta.McMeta;
import fr.az.crispack.core.load.meta.McMetaReader;
import fr.az.crispack.core.pack.DataPack;
import fr.az.crispack.core.pack.PackIdentity;
import fr.az.crispack.core.pack.PackType;

public class DirectoryDatapackLoader implements DatapackLoader
{
	private static final McMetaReader META_READER = new McMetaReader();

	private final Path dir;

	public DirectoryDatapackLoader(Path path)
	{
		if (Files.isDirectory(path))
			this.dir = path;
		else
			this.dir = null;
	}

	@Override
	public Optional<DataPack> loadDataPackUnhandled(String save) throws PackLoadingException
	{
		if (this.dir == null)
			return Optional.empty();

		Path mcmeta = this.dir.resolve("pack.mcmeta");
		McMeta meta;

		try
		{
			meta = META_READER.read(mcmeta.toFile());
		} catch (InvalidMetaException e)
		{
			throw new PackLoadingException("Could not read pack.mcmeta for datapack %s [%s]:\n\t - %s".formatted
			(
				this.dir.getFileName().toString(),
				save,
				e.getMessage()
			));
		}

		return Optional.of(new DataPack(meta, new PackIdentity(meta.author(), meta.name(), meta.version(), PackType.DATAPACK)));
	}
}
