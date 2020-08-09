package fr.az.cytokine.domain;

import java.nio.file.Path;

import fr.az.cytokine.app.dependency.extract.DependencyExtractor;

public interface DependencyExtractorFactory
{
	DependencyExtractor of(Path path);
}
