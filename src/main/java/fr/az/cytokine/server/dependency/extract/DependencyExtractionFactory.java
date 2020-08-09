package fr.az.cytokine.server.dependency.extract;

import java.nio.file.Files;
import java.nio.file.Path;

import fr.az.cytokine.app.dependency.extract.DependencyExtractor;
import fr.az.cytokine.domain.DependencyExtractorFactory;

public class DependencyExtractionFactory implements DependencyExtractorFactory
{
	@Override
	public DependencyExtractor get(Path path)
	{
		if (Files.isDirectory(path))
			return FolderDependencyExtractor.of(path);

		if (Files.isRegularFile(path) && Files.isReadable(path))
			return this.ofFile(path);

		return new EmptyExtractor(path);
	}

	private DependencyExtractor ofFile(Path path)
	{
		if (path.getFileName().toString().equals("pack.mcmeta"))
			return JSONDependencyExtractor.file(path);

		if (path.getFileName().toString().endsWith(".zip"))
			return ZipDependencyExtractor.of(path);

		return new EmptyExtractor(path);
	}
}
