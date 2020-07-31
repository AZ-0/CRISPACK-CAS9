package fr.az.crispack.core.load.datapack;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;

import fr.az.crispack.core.dependency.Dependency;
import fr.az.crispack.core.dependency.extract.JSONDependencyExtractor;
import fr.az.crispack.core.load.PackLoadingException;
import fr.az.crispack.core.pack.DataPack;
import fr.az.crispack.core.pack.PackIdentity;
import fr.az.crispack.core.pack.PackType;
import fr.az.crispack.core.version.Version;
import fr.az.crispack.util.Util;

public class DirectoryDatapackLoader implements DatapackLoader
{
	private final Path dir;

	public DirectoryDatapackLoader(Path path)
	{
		if (Files.isDirectory(path))
			this.dir = path;
		else
			this.dir = null;
	}

	@Override
	public Optional<DataPack> loadFrom(String save) throws PackLoadingException
	{
		if (this.dir == null)
			return Optional.empty();

		Path mcmeta = this.dir.resolve("pack.mcmeta");
		return Optional.ofNullable(this.read(mcmeta));
	}

	private DataPack read(Path file) throws PackLoadingException
	{
		JSONObject meta = new JSONObject(Util.safeOp(file, Files::readString));

		PackIdentity identity = this.getIdentity(meta.optJSONObject("pack"));
		List<Dependency> dependencies = JSONDependencyExtractor.file(meta, file).extract();

		return new DataPack(identity, dependencies);
	}

	private PackIdentity getIdentity(JSONObject obj) throws PackLoadingException
	{
		if (obj == null)
			throw new PackLoadingException("Error in pack.mcmeta: Missing mandatory json key 'pack'");

		String author	= obj.optString("author", null);
		String name		= obj.optString("name", null);
		String version	= obj.optString("name", null);

		if (author == null || name == null || version == null)
			throw new PackLoadingException("Error in pack.mcmeta: Missing either of 'author', 'name' or 'version' key in 'pack'");

		return new PackIdentity(author, name, new Version(version), PackType.DATAPACK);
	}
}
