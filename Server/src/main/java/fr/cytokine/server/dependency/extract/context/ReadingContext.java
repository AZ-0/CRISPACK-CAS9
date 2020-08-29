package fr.cytokine.server.dependency.extract.context;

import java.nio.file.Path;

public interface ReadingContext
{
	Path path();

	default boolean isZip()  { return false; }
	default boolean isFile() { return false; }

	default ZipReadingContext	asZip()  { return null; }
	default FileReadingContext	asFile() { return null; }
}
