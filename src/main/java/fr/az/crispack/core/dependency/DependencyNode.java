package fr.az.crispack.core.dependency;

import fr.az.crispack.util.trees.Node;
import fr.az.crispack.util.trees.NodeFactory;
import fr.az.crispack.util.trees.RootManager;

import reactor.core.publisher.Flux;

public class DependencyNode extends Node<DependencyNode, DependencyIdentity>
{
	private static final long serialVersionUID = 3600285215880142304L;

	public static final RootManager<DependencyNode, DependencyIdentity> ROOTS = new RootManager<>(DependencyNode::new);

	public static DependencyNode root(Dependency from)
	{
		return ROOTS.getOrNewRoot(new DependencyIdentity(from));
	}

	// ROOT CONSTRUCTOR
	private DependencyNode(DependencyIdentity identity)
	{
		super(identity, DependencyNode::new);
	}

	// NODE CONSTRUCTOR
	private DependencyNode(DependencyNode parent, DependencyIdentity identity, NodeFactory<DependencyNode, DependencyIdentity> factory)
	{
		super(parent, identity, factory);
	}

	public Dependency dependency() { return this.identity().dependency(); }
	public boolean hasVersion() { return this.dependency().hasVersion(); }
	public VersionedDependency withVersion() { return this.dependency().withVersion(); }

	public Flux<DependencyNode> collect() {
		// TODO Auto-generated method stub
		return null;
	}
}
