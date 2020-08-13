package fr.az.cytokine.server.load.datapack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.app.pack.DataPack;
import fr.az.cytokine.app.pack.PackIdentity;
import fr.az.cytokine.app.pack.PackType;
import fr.az.cytokine.app.version.Version;
import fr.az.cytokine.domain.dependency.extract.DependencyExtractionException;
import fr.az.cytokine.server.dependency.extract.JSONDependencyExtractor;
import fr.az.cytokine.server.load.PackLoadingException;

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
		PackIdentity identity;
		List<Dependency> dependencies;

		try
		{
			JSONObject meta = new JSONObject(Files.readString(file));
			identity = this.getIdentity(meta.optJSONObject("pack"));
			dependencies = JSONDependencyExtractor.file(meta, file).extract();
		}
		catch (IOException | JSONException | DependencyExtractionException e)
		{
			throw new PackLoadingException("Could not read dependencies for datapack: "+ e.getMessage());
		}

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
