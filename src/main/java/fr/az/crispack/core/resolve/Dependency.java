package fr.az.crispack.core.resolve;

import fr.az.crispack.core.Repository;
import fr.az.crispack.util.trees.Node;
import fr.az.crispack.util.trees.NodeFactory;
import fr.az.crispack.util.trees.RootManager;

import reactor.core.publisher.Flux;

public class Dependency extends Node<Dependency, DependencyIdentity>
{
	private static final long serialVersionUID = 3600285215880142304L;

	public static final RootManager<Dependency, DependencyIdentity> ROOTS = new RootManager<>(Dependency::new);

	private Dependency(DependencyIdentity identity)
	{
		super(identity, Dependency::new);
	}

	private Dependency(Dependency parent, DependencyIdentity identity, NodeFactory<Dependency, DependencyIdentity> factory)
	{
		super(parent, identity, factory);
	}

	public Flux<Dependency> collect() { return this.getRepository().collect(this); }
	public Repository getRepository() { return this.getIdentity().repository(); }
}
