package fr.az.cytokine.domain.dependency.extract;

import java.util.List;

import fr.az.cytokine.app.dependency.Dependency;

public interface DependencyExtractor
{
	List<Dependency> extract() throws DependencyExtractionException;
}
