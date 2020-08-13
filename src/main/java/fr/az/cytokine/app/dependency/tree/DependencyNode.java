package fr.az.cytokine.app.dependency.tree;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import fr.az.cytokine.app.dependency.Dependency;

public class DependencyNode
{
	private final Dependency dependency;
	private final Set<DependencyNode> children;
	private final Set<DependencyNode> childrenImmutable;

	public DependencyNode(Dependency dependency)
	{
		this.dependency = dependency;
		this.children = new HashSet<>();
		this.childrenImmutable = Collections.unmodifiableSet(this.children);
	}

	public DependencyNode makeChild(Dependency dependency)
	{
		DependencyNode child = new DependencyNode(dependency);
		this.children.add(child);
		return child;
	}

	public void visit(DependencyTreeVisitor visitor) { this.visit(visitor, 0); }

	private void visit(DependencyTreeVisitor visitor, int depth)
	{
		visitor.visit(this, depth);
		this.children.forEach(child -> child.visit(visitor, depth +1));
	}

	public Dependency dependency() { return this.dependency; }
	public Set<DependencyNode> children() { return this.childrenImmutable; }

	@Override public boolean equals(Object obj) {
		return obj instanceof DependencyNode node && node.dependency.equals(this.dependency); }

	@Override public int hashCode() {
		return this.dependency().hashCode(); }
}
