package fr.az.cytokine.app.dependency;

import fr.az.cytokine.util.trees.Node;
import fr.az.cytokine.util.trees.NodeFactory;
import fr.az.cytokine.util.trees.RootManager;

import reactor.core.publisher.Flux;

public class DependencyNode extends Node<DependencyNode, DependencyNode.Identity>
{
	private static final long serialVersionUID = 3600285215880142304L;

	public static final RootManager<DependencyNode, Identity> ROOTS = new RootManager<>(DependencyNode::new);

	public static DependencyNode root(Dependency from)
	{
		return ROOTS.getOrNewRoot(new Identity(from));
	}

	// ROOT CONSTRUCTOR
	private DependencyNode(Identity identity)
	{
		super(identity, DependencyNode::new);
	}

	// NODE CONSTRUCTOR
	private DependencyNode(DependencyNode parent, Identity identity, NodeFactory<DependencyNode, Identity> factory)
	{
		super(parent, identity, factory);
	}

	public Flux<DependencyNode> collect()
	{
		return this.identity().dependency().collect().map(Identity::new).map(this::getOrNewChild);
	}

	public static record Identity(Dependency dependency) {}
}
