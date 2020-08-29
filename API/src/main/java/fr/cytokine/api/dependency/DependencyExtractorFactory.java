package fr.cytokine.api.dependency;

import fr.cytokine.api.dependency.extract.DependencyExtractor;

import java.nio.file.Path;

public interface DependencyExtractorFactory
{
	DependencyExtractor get(Path path);
}
