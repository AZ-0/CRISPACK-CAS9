package fr.az.registry.core.pack.content;

import java.nio.file.Path;
import java.util.zip.ZipFile;

public interface PackContent
{
	Path path();
	ZipFile zip();
}
