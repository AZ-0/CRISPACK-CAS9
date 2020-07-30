package fr.az.crispack.core.dependency;

import fr.az.crispack.core.pack.PackType;

public record NodeIdentity(Dependency dependency)
{
	public PackType type() { return this.dependency.type(); }
}
