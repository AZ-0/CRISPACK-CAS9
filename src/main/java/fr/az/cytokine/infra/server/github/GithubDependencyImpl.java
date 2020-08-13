package fr.az.cytokine.infra.server.github;

import fr.az.cytokine.api.core.dependency.GithubDependency;
import fr.az.cytokine.api.core.pack.PackType;
import fr.az.cytokine.api.core.version.Version;

public record GithubDependencyImpl(String author, String name, PackType type, Version version) implements GithubDependency
{
	@Override
	public String toString()
	{
		return "github[%s, %s:%s:%s]".formatted(this.type.dirName(), this.author, this.name, this.version);
	}
}
