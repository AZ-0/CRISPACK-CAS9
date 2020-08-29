package fr.cytokine.server.dependency.extract;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import fr.az.cytokine.domain.dependency.DependencyExtractorFactory;
import fr.az.cytokine.domain.dependency.extract.DependencyExtractor;

public class DependencyExtractorFactoryImpl implements DependencyExtractorFactory
{
	@Override
	public DependencyExtractor get(Path path)
	{
		Objects.requireNonNull(path);

		if (Files.isDirectory(path))
			return FolderDependencyExtractor.of(path);

		if (Files.isRegularFile(path) && Files.isReadable(path))
			return ofFile(path);

		return new EmptyExtractor(path);
	}

	private static DependencyExtractor ofFile(Path path)
	{
		String fileName = path.getFileName().toString();

		if (fileName.equals("pack.mcmeta"))
			return JSONDependencyExtractor.file(path);

		if (fileName.endsWith(".zip"))
			return ZipDependencyExtractor.of(path);

		return new EmptyExtractor(path);
	}
}
