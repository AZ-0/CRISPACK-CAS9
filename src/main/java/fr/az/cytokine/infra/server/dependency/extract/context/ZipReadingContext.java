package fr.az.cytokine.infra.server.dependency.extract.context;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ZipReadingContext implements ReadingContext
{
	private final Path path;

	public ZipReadingContext(Path path)
	{
		this.path = Objects.requireNonNull(path, "path may not be null");
	}

	public ZipFile zip() throws ZipException, IOException
	{
		return new ZipFile(this.path.toFile());
	}

	@Override public Path path() { return this.path; }

	@Override public boolean isZip() { return true; }
	@Override public ZipReadingContext asZip() { return this; }
}
