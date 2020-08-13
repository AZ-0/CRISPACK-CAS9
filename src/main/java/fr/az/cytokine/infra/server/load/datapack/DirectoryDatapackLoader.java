package fr.az.cytokine.infra.server.load.datapack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import fr.az.cytokine.app.pack.DataPack;
import fr.az.cytokine.infra.server.json.Keys;
import fr.az.cytokine.infra.server.load.PackLoadingException;
import fr.az.util.parsing.json.JSONParsingException;

class DirectoryDatapackLoader implements DatapackLoader
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
		DataPack pack;

		try
		{
			JSONObject meta = new JSONObject(Files.readString(file));
			pack = this.getPack(file, meta);
		}
		catch (IOException | JSONException e)
		{
			throw new PackLoadingException("Could not read dependencies for datapack: "+ e.getMessage());
		}

		return pack;
	}

	private DataPack getPack(Path path, JSONObject content) throws PackLoadingException
	{
		if (content == null)
			throw new PackLoadingException("Error in pack.mcmeta: Missing mandatory json key 'pack'");

		try
		{
			return Keys.DATAPACK.parse(content);
		}
		catch (JSONParsingException e)
		{
			throw new PackLoadingException(e.getMessage());
		}
	}
}
