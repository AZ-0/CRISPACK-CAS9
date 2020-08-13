package fr.az.cytokine.app.dependency;

public interface GithubDependency extends VersionedDependency
{
	String author();
	String name();

	@Override
	default <T> T visit(DependencyVisitor<T> visitor)
	{
		return visitor.visit(this);
	}
}
