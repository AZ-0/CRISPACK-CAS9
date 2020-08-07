package fr.az.cytokine.domain.pack;

import fr.az.cytokine.domain.version.Version;

public record PackIdentity(String author, String name, Version version, PackType type)
{
	public boolean isEmpty()
	{
		return this.author == null
			|| this.name == null
			|| this.version == null
			|| this.version.raw() == null
			|| this.author.isBlank()
			|| this.name.isBlank()
			|| this.version.raw().isBlank();
	}
}
