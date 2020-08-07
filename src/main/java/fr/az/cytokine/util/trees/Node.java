package fr.az.cytokine.util.trees;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This class represents a Node in a tree.
 * @author AZ
 *
 * @param <N> the core {@linkplain Node} implementation in a tree.
 */
public class Node<N extends Node<N, I>, I> implements Serializable
{
	private static final long serialVersionUID = 2065849983131455447L;

	final Map<String, N> children;
	private final Map<String, N> childrenImmutable;

	private final NodeFactory<N, I> factory;
	private final N parent;
	private final I identity;

	protected Node(I identity, NodeFactory<N, I> factory)
	{
		this(null, identity, factory);
	}

	protected Node(N parent, I identity, NodeFactory<N, I> factory)
	{
		this(parent, identity, new HashMap<>(), factory);
	}

	Node(N parent, I identity, Map<String, N> children, NodeFactory<N, I> factory)
	{
		this.children = children;
		this.childrenImmutable = Collections.unmodifiableMap(children);

		this.factory = factory;
		this.parent = parent;
		this.identity = identity;
	}

	/**
	 * Navigates to the target node until it is reached. Each time null is encountered, creates a child to pursue navigation.
	 * <br>The returned {@linkplain Optional} being empty is equivalent to the provided path being empty.
	 *
	 * @param path the path to the target from this node.
	 * @return the target node.
	 * @see #navigate(String, Function)
	 */
	public Optional<N> makePath(I[] path)
	{
		return this.navigate(path, Node::getOrNewChild);
	}

	/**
	 * Navigates to the target node until it is reached or null is encountered.
	 * <br>The returned {@linkplain Optional} is only full if the navigation completed successfully.
	 *
	 * @param path the path to the target from this node.
	 * @return the target node, or null if it doesn't exist.
	 * @see #navigate(String, Function)
	 */
	public Optional<N> getChild(I[] path)
	{
		return this.navigate(path, (node, identity) -> node.getChild(identity).orElse(null));
	}

	/**
	 * Navigates within the tree to the target from this node.
	 * If at any point the navigation completes empty, returns an empty optional (ie navigation failure)
	 *
	 * <p>
	 * <strong>Edge Case:</strong>
	 * <br> • If the path is empty, return an empty Optional
	 *
	 * @param path the path to the target from this node.
	 * @param navigator a function called in each iteration, getting to the next point toward the destination.
	 * @return an {@linkplain Optional} representing the target node.
	 * @see Node#getNode(String)
	 * @see Node#makePath(String)
	 */
	public Optional<N> navigate(I[] path, BiFunction<? super N, ? super I, ? extends N> navigator)
	{
		N node = this.cast();

		for (I identity : path)
		{
			node = navigator.apply(node, identity);

			if (node == null)
				return Optional.empty();
		}

		return Optional.of(node);
	}

	/**
	 * Try to get a
	 * @param identity
	 * @return
	 * @see Node#getChild(String)
	 */
	public N getOrNewChild(I identity)
	{
		Optional<N> child = this.getChild(identity);

		if (!child.isPresent())
			return this.factory.produce(this.cast(), identity, this.factory);

		return child.get();
	}

	/**
	 * Gets the root of the tree, by recursive calls to {@linkplain Node#getParent}.
	 * @return the root
	 */
	public Node<N, I> getRoot()
	{
		Node<N, I> node = this;

		while (!node.isRoot())
			node = node.parent();

		return node;
	}

	/**
	 * Checks wether the provided local name is associated to a child of this node.
	 * @param identity the name of the child, inside this node.
	 * @return wether the child exists.
	 * @see Node#getLocalName()
	 */
	public boolean hasChild(I identity) { return this.children().containsKey(identity); }

	/**
	 * Get a child of this node, associated to the provided local name.
	 * <br>If the child does not exists, return an empty {@linkplain Optional}. Otherwise it is only wrapped.
	 *
	 * @param identity the name of the child, inside this node.
	 * @return an {@linkplain Optional} representing this node's child.
	 * @see Node#getLocalName()
	 */
	public Optional<N> getChild(I identity) { return Optional.ofNullable(this.children.get(identity)); }

	@SuppressWarnings("unchecked")
	public N cast() { return (N) this; }

	/** @return wether this node is a root node. */
	public boolean isRoot() { return this.parent == null; }

	/** @return this node's children, as an immutable map. */
	public Map<String, N> children() { return this.childrenImmutable; }

	public N parent() { return this.parent; }

	/**
	 * The local name of a node is its unique identifier <i>within its parent's children batch</i>.
	 * For instance, another node in the same tree may have the same local name, but wouldn't have the same parent.
	 * As such, the unique identifier of a node in a tree is its full name.
	 * <p>
	 * Use the local name for getting a child, and for {@linkplain #navigate(String, Function) navigation} within the tree.
	 * <br>Note : navigation requires you to stack a child's local name to its parent's with <code>.</code>
	 * @return the local name of this node.
	 */
	public I identity() { return this.identity; }
}
