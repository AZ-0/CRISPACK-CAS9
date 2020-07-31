package fr.az.crispack.core.dependency.context;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public record ZipReadingContext(Path path, ZipFile zip) implements ReadingContext
{
	public ZipReadingContext(Path path) throws ZipException, IOException
	{
		this(path, new ZipFile(path.toFile()));
	}

	public ZipReadingContext
	{
		path = Objects.requireNonNull(path, "path may not be null");
		zip = Objects.requireNonNull(zip, "zip file may not be null");
	}

	@Override public boolean isZip() { return true; }
	@Override public ZipReadingContext asZip() { return this; }
}
