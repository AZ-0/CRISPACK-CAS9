package fr.az.cytokine.domain.pack;

import fr.az.cytokine.domain.version.Version;

public interface Pack
{
	String author();
	String name();
	Version version();
}
