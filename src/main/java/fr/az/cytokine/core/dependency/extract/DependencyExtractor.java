package fr.az.cytokine.core.dependency.extract;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import fr.az.cytokine.core.dependency.Dependency;
import fr.az.cytokine.core.dependency.context.ReadingContext;
import fr.az.cytokine.util.Util;

public abstract class DependencyExtractor
{
	private final ReadingContext context;

	public static DependencyExtractor of(Path path)
	{
		if (Util.existsDir(path))
			return FolderDependencyExtractor.of(path);

		if (Util.existsFile(path) && Files.isReadable(path))
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
