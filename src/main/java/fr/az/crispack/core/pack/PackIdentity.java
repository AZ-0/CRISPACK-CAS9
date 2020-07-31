package fr.az.crispack.core.pack;

import fr.az.crispack.core.Identity;
import fr.az.crispack.core.version.Version;

public record PackIdentity(String author, String name, Version version, PackType type) implements Identity
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
