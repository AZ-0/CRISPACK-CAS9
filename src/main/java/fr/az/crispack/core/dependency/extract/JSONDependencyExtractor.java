package fr.az.crispack.core.dependency.extract;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.json.JSONObject;

import fr.az.crispack.core.dependency.Dependency;
import fr.az.crispack.core.dependency.context.FileReadingContext;
import fr.az.crispack.core.dependency.context.ReadingContext;
import fr.az.crispack.core.dependency.context.ZipReadingContext;
import fr.az.crispack.json.dependency.KeyDependencies;

public class JSONDependencyExtractor extends DependencyExtractor
{
	private final JSONObject meta;

	public static JSONDependencyExtractor file(JSONObject meta, Path path)
	{
		return new JSONDependencyExtractor(meta, new FileReadingContext(path));
	}

	public static JSONDependencyExtractor zip(JSONObject meta, Path path) throws ZipException, IOException
	{
		return new JSONDependencyExtractor(meta, new ZipReadingContext(path));
	}

	public static JSONDependencyExtractor zip(JSONObject meta, Path path, ZipFile zip)
	{
		return new JSONDependencyExtractor(meta, new ZipReadingContext(path, zip));
	}

	public JSONDependencyExtractor(JSONObject meta, ReadingContext context)
	{
		super(context);
		this.meta = meta;
	}

	@Override
	public List<Dependency> extract()
	{
		if (this.meta == null)
			return List.of();

		JSONObject dependencies = this.meta.optJSONObject("dependencies");

		return new KeyDependencies(this.context()).parse(dependencies, "pack.mcmeta");
	}
}
