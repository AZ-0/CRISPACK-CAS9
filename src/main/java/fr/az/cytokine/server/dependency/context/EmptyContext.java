package fr.az.cytokine.server.dependency.context;

import java.nio.file.Path;

public record EmptyContext(Path path) implements ReadingContext
{

}
