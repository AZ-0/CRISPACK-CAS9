package fr.az.cytokine.core.dependency;

import fr.az.cytokine.core.pack.PackType;

public record NodeIdentity(Dependency dependency)
{
	public PackType type() { return this.dependency.type(); }
}
