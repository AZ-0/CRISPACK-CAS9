package fr.az.cytokine.domain.dependency;

import fr.az.cytokine.domain.pack.PackType;

public record NodeIdentity(Dependency dependency)
{
	public PackType type() { return this.dependency.type(); }
}
