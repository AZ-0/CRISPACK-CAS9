package fr.az.cytokine.api.core.dependency.tree;

public interface DependencyTreeVisitor<T extends Throwable>
{
	void visit(DependencyNode node, int depth) throws T;
}
