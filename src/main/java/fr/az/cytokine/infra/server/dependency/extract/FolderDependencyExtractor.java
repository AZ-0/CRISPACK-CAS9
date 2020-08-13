package fr.az.cytokine.infra.server.dependency.extract;

import java.nio.file.Path;
import java.util.List;

import fr.az.cytokine.api.core.dependency.Dependency;
import fr.az.cytokine.domain.dependency.extract.DependencyExtractionException;
import fr.az.cytokine.domain.dependency.extract.DependencyExtractor;

class FolderDependencyExtractor implements DependencyExtractor
{
	public static FolderDependencyExtractor of(Path folder)
	{
		return new FolderDependencyExtractor(folder.resolve("pack.mcmeta"));
	}

	private final DependencyExtractor proxy;

	public FolderDependencyExtractor(Path meta)
	{
		this.proxy = new DependencyExtractorFactoryImpl().get(meta);
	}

	@Override
	public List<Dependency> extract() throws DependencyExtractionException
	{
		return this.proxy.extract();
	}
}
