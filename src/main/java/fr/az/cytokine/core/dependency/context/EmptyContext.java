package fr.az.cytokine.core.dependency.context;

import java.nio.file.Path;

public record EmptyContext(Path path) implements ReadingContext
{

}
