package fr.az.cytokine.server.dependency.extract;

import java.nio.file.Path;
import java.util.List;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.app.dependency.extract.DependencyExtractionException;
import fr.az.cytokine.server.dependency.context.FileReadingContext;

public class FolderDependencyExtractor extends DependencyExtractor
{
	public static FolderDependencyExtractor of(Path folder)
	{
		return new FolderDependencyExtractor(new FileReadingContext(folder));
	}

	private final DependencyExtractor proxy;

	public FolderDependencyExtractor(FileReadingContext context)
	{
		super(context);
		Path path = this.context().path().resolve("pack.mcmeta");
		this.proxy = DependencyExtractor.of(path);
	}

	@Override
	public List<Dependency> extract() throws DependencyExtractionException
	{
		return this.proxy.extract();
	}
}
