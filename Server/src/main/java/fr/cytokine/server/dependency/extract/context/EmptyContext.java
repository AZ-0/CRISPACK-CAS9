package fr.cytokine.server.dependency.extract.context;

import java.nio.file.Path;

public record EmptyContext(Path path) implements ReadingContext
{

}
