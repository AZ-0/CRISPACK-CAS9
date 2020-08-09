package fr.az.cytokine.server.dependency.extract;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.app.dependency.extract.DependencyExtractionException;
import fr.az.cytokine.server.dependency.context.FileReadingContext;
import fr.az.cytokine.server.dependency.context.ReadingContext;
import fr.az.cytokine.server.dependency.context.ZipReadingContext;
import fr.az.cytokine.server.json.dependency.KeyDependencies;

public class JSONDependencyExtractor extends DependencyExtractor
{
	public static JSONDependencyExtractor file(Path path) { return file(null, path); }

	public static JSONDependencyExtractor file(JSONObject meta, Path path) {
		return new JSONDependencyExtractor(meta, new FileReadingContext(path)); }

	public static JSONDependencyExtractor zip(Path path) { return zip(null, path); }

	public static JSONDependencyExtractor zip(JSONObject meta, Path path) {
		return new JSONDependencyExtractor(meta, new ZipReadingContext(path)); }


	private final JSONObject meta;

	public JSONDependencyExtractor(JSONObject meta, ReadingContext context)
	{
		super(context);
		this.meta = meta;
	}

	@Override
	public List<Dependency> extract() throws DependencyExtractionException
	{
		JSONObject meta = this.meta;

		if (meta == null && this.context().isZip())
			return new ZipDependencyExtractor(this.context().asZip()).extract();

		if (meta == null && this.context().isFile())
			try
			{
				meta = new JSONObject(Files.readString(this.context().path()));
			}
			catch (IOException | JSONException e)
			{
				throw new DependencyExtractionException(e.getMessage());
			}

		if (meta == null)
			throw new DependencyExtractionException("Unable to load pack meta for '%s'".formatted(this.context().path()));

		JSONObject dependencies = meta.optJSONObject("dependencies");

		return new KeyDependencies(this.context()).parse(dependencies, "pack.mcmeta");
	}
}
