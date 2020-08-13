package fr.az.cytokine.infra.server.dependency.extract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipFile;

import org.json.JSONException;
import org.json.JSONObject;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.domain.dependency.extract.DependencyExtractionException;
import fr.az.cytokine.infra.server.dependency.extract.context.ZipReadingContext;

class ZipDependencyExtractor extends AbstractDependencyExtractor
{
	public static ZipDependencyExtractor of(Path file)
	{
		return new ZipDependencyExtractor(new ZipReadingContext(file));
	}

	public ZipDependencyExtractor(ZipReadingContext context)
	{
		super(context);
	}

	@Override
	public List<Dependency> extract() throws DependencyExtractionException
	{
		try
		(
			ZipFile zip = this.context().asZip().zip();
			BufferedReader br = new BufferedReader(new InputStreamReader(zip.getInputStream(zip.getEntry("pack.mcmeta"))))
		)
		{
			String content = br.lines().reduce("", String::concat);
			return new JSONDependencyExtractor(new JSONObject(content), this.context()).extract();
		}
		catch (IOException | JSONException e)
		{
			throw new DependencyExtractionException(e.getMessage());
		}
	}
}
