package fr.az.cytokine.infra.server.dependency.context;

import java.nio.file.Path;

public record EmptyContext(Path path) implements ReadingContext
{

}
