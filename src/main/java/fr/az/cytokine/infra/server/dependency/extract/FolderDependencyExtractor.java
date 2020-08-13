package fr.az.cytokine.infra.server.dependency.extract;

import java.nio.file.Path;
import java.util.List;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.domain.dependency.extract.DependencyExtractionException;
import fr.az.cytokine.domain.dependency.extract.DependencyExtractor;
import fr.az.cytokine.infra.server.dependency.context.FileReadingContext;

public class FolderDependencyExtractor extends AbstractDependencyExtractor
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
		this.proxy = new DependencyExtractionFactory().get(path);
	}

	@Override
	public List<Dependency> extract() throws DependencyExtractionException
	{
		return this.proxy.extract();
	}
}
