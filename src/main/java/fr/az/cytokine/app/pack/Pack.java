package fr.az.cytokine.app.pack;

import fr.az.cytokine.app.version.Version;

public interface Pack
{
	String author();
	String name();
	Version version();
}
