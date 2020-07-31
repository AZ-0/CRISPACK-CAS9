package fr.az.crispack.core.dependency;

import fr.az.crispack.util.trees.Node;
import fr.az.crispack.util.trees.NodeFactory;
import fr.az.crispack.util.trees.RootManager;

import reactor.core.publisher.Flux;

public class DependencyNode extends Node<DependencyNode, NodeIdentity>
{
	private static final long serialVersionUID = 3600285215880142304L;

	public static final RootManager<DependencyNode, NodeIdentity> ROOTS = new RootManager<>(DependencyNode::new);

	public static DependencyNode root(Dependency from)
	{
		return ROOTS.getOrNewRoot(new NodeIdentity(from));
	}

	// ROOT CONSTRUCTOR
	private DependencyNode(NodeIdentity identity)
	{
		super(identity, DependencyNode::new);
	}

	// NODE CONSTRUCTOR
	private DependencyNode(DependencyNode parent, NodeIdentity identity, NodeFactory<DependencyNode, NodeIdentity> factory)
	{
		super(parent, identity, factory);
	}

	public Dependency dependency() { return this.identity().dependency(); }
	public boolean hasVersion() { return this.dependency().hasVersion(); }
	public VersionedDependency withVersion() { return this.dependency().withVersion(); }

	public Flux<DependencyNode> collect()
	{
		return this.dependency().collect().map(NodeIdentity::new).map(this::getOrNewChild);
	}
}
