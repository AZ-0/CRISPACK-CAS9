package fr.az.crispack.core.pack.content;

import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipFile;

public record DataPackContent(Path path, ZipFile zip) implements PackContent
{
	public DataPackContent(Path path) throws IOException
	{
		this.path = path;
		this.zip = new ZipFile(path.toFile());
	}
}
