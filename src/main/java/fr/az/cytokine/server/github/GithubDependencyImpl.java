package fr.az.cytokine.server.github;

import fr.az.cytokine.app.dependency.GithubDependency;
import fr.az.cytokine.app.pack.PackType;
import fr.az.cytokine.app.version.Version;

public record GithubDependencyImpl(String author, String name, PackType type, Version version) implements GithubDependency
{
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof GithubDependency dependency
			&& dependency.author().equals(this.author())
			&& dependency.name().equals(this.name())
			&& dependency.type().equals(this.type())
			&& dependency.version().equals(this.version());
	}

	@Override
	public String toString()
	{
		return "github[%s, %s:%s:%s]".formatted(this.type.dirName(), this.author, this.name, this.version);
	}
}
