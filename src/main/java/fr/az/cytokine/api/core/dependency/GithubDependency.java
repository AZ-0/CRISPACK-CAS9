package fr.az.cytokine.api.core.dependency;

public interface GithubDependency extends VersionedDependency
{
	String author();
	String name();

	@Override
	default <T> T accept(DependencyVisitor<T> visitor)
	{
		return visitor.visit(this);
	}
}
