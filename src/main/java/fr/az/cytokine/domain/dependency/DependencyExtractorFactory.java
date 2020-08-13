package fr.az.cytokine.domain.dependency;

import java.nio.file.Path;

import fr.az.cytokine.domain.dependency.extract.DependencyExtractor;

public interface DependencyExtractorFactory
{
	DependencyExtractor get(Path path);
}
