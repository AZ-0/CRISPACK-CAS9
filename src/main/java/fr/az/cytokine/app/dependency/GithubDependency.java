package fr.az.cytokine.app.dependency;

public interface GithubDependency extends VersionedDependency
{
	String author();
	String name();
}
