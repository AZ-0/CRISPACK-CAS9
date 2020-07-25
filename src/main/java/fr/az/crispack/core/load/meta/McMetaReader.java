package fr.az.crispack.core.load.meta;

import java.io.File;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.az.crispack.core.pack.PackType;
import fr.az.crispack.core.resolve.Dependency;
import fr.az.crispack.core.version.Version;
import fr.az.crispack.core.version.version.LocalVersion;
import fr.az.crispack.util.Utils;

public class McMetaReader
{
	public McMeta read(File mcmeta) throws InvalidMetaException
	{
		if (mcmeta == null || !mcmeta.exists() || !mcmeta.isFile())
			throw new InvalidMetaException("Could not resolve pack.mcmeta");

		JSONObject obj;

		try
		{
			obj = new JSONObject(Utils.getContent(mcmeta));
			this.checkHas("base compound", obj, "meta");
		} catch (JSONException e)
		{
			throw new InvalidMetaException("Invalid JSON: "+ e.getMessage());
		}

		JSONObject meta = obj.optJSONObject("meta");
		this.checkHas("meta", meta, "type", "name", "author", "version");

		PackType type	= meta.getEnum(PackType.class, "type");
		String name		= meta.getString("name");
		String author	= meta.getString("author");
		Version version	= new LocalVersion(meta.getString("version"), null);

		Set<String> tags				= this.readTags(meta.optJSONArray("tags"));
		Set<Dependency> dependencies	= this.readDependencies(meta.optJSONArray("dependencies"));

		return new McMeta(type, name, author, version, tags, dependencies);
	}

	public Set<String> readTags(JSONArray array) throws InvalidMetaException
	{
		Set<String> tags = new HashSet<>();

		if (array == null)
			return tags;

		for (Object tag : array)
		{
			this.checkType("tag", String.class, tag.getClass());
			tags.add(tag.toString());
		}

		return tags;
	}

	public Set<Dependency> readDependencies(JSONArray array) throws InvalidMetaException
	{
		Set<Dependency> dependencies = new HashSet<>();

		if (array == null)
			return dependencies;

		for (Object obj : array)
		{
			this.checkType("dependency", JSONObject.class, obj.getClass());
			this.readDependency((JSONObject) obj).ifPresent(dependencies::add);
		}

		return dependencies;
	}

	public Optional<Dependency> readDependency(JSONObject obj) throws InvalidMetaException
	{
		return Optional.empty();
	}

	private void checkHas(String name, JSONObject obj, String... keys) throws InvalidMetaException
	{
		if (obj == null)
			throw new InvalidMetaException("Invalid %s key, expected a compound".formatted(name));

		Set<String> missing = new HashSet<>();

		for (String key : keys)
			if (!obj.has(key))
				missing.add(key);

		if (!missing.isEmpty())
			throw new InvalidMetaException("Missing mandatory keys in %s: %s".formatted(name, missing));
	}

	private void checkType(String name, Class<?> expected, Class<?> provided) throws InvalidMetaException
	{
		if (expected != provided)
			throw new InvalidMetaException("Invalid %s found. Expected %s but %s was provided".formatted
			(
				name,
				expected.getSimpleName(),
				provided.getSimpleName()
			));
	}
}
