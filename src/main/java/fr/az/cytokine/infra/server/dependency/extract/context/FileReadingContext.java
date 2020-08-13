package fr.az.cytokine.infra.server.dependency.extract.context;

import java.nio.file.Path;

public record FileReadingContext(Path path) implements ReadingContext
{
	@Override public boolean isFile() { return true; }
	@Override public FileReadingContext asFile() { return this; }
}
