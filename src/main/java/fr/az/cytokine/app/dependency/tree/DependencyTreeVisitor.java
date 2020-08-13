package fr.az.cytokine.app.dependency.tree;

public interface DependencyTreeVisitor
{
	void visit(DependencyNode node, int depth);
}
