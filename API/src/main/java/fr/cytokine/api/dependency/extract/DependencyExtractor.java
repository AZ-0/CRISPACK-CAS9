package fr.cytokine.api.dependency.extract;

import fr.cytokine.api.dependency.Dependency;

import java.util.List;

public interface DependencyExtractor
{
	List<Dependency> extract() throws DependencyExtractionException;
}
