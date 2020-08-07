package fr.az.cytokine.server.dependency.extract;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import fr.az.cytokine.domain.dependency.Dependency;
import fr.az.cytokine.server.dependency.context.ReadingContext;

public abstract class DependencyExtractor
{
	private final ReadingContext context;

	public static DependencyExtractor of(Path path)
	{
		if (Files.isDirectory(path))
			return FolderDependencyExtractor.of(path);

		if (Files.isRegularFile(path) && Files.isReadable(path))
			return ofFile(path);

		return new EmptyExtractor(path);
	}

	private static DependencyExtractor ofFile(Path path)
	{
		if (path.getFileName().toString().equals("pack.mcmeta"))
			return JSONDependencyExtractor.file(path);

		if (path.getFileName().toString().endsWith(".zip"))
			return ZipDependencyExtractor.of(path);

		return new EmptyExtractor(path);
	}

	public DependencyExtractor(ReadingContext context)
	{
		this.context = Objects.requireNonNull(context, "can only extract with non-null reading context");
	}

	public ReadingContext context() { return this.context; }

	public abstract List<Dependency> extract() throws DependencyExtractionException;
}
