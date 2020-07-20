package fr.az.crispack.core.pack.content;

import java.nio.file.Path;
import java.util.zip.ZipFile;

public interface PackContent
{
	Path path();
	ZipFile zip();
}
