package fr.az.cytokine.server.dependency.extract;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.app.dependency.extract.DependencyExtractionException;
import fr.az.cytokine.server.dependency.context.EmptyContext;

public class EmptyExtractor extends DependencyExtractor
{
	public EmptyExtractor(Path path)
	{
		super(new EmptyContext(path));
	}

	@Override
	public List<Dependency> extract() throws DependencyExtractionException
	{
		Path path = this.context().path();

		if (!Files.exists(path))
			throw new DependencyExtractionException("Nothing found at '%s'".formatted(path));

		if (!Files.isReadable(path))
			throw new DependencyExtractionException("Unable to read file at '%s'".formatted(path));

		throw new DependencyExtractionException("Could not extract dependencies from '%s'".formatted(path));
	}
}
