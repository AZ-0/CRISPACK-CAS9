package fr.az.crispack.util.trees;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import fr.az.crispack.util.trees.visit.TreeVisitor;
import fr.az.crispack.util.trees.visit.VisitSignal;

/**
 * A helper class for managing trees.
 * @author AZ
 *
 * @param <N> the core {@linkplain Node} implementation for the trees.
 */
public class RootManager<N extends Node<N, I>, I>
{
	private final Function<I, N> rootProducer;
	private final Map<I, N> roots;

	/**
	 * Constructor.
	 * @param rootProducer a {@linkplain Function} used to control root production in {@linkplain RootManager#getOrNewRoot(String)}.
	 */
	public RootManager(Function<I, N> rootProducer)
	{
		this.rootProducer = rootProducer;
		this.roots = new HashMap<>();
	}

	/**
	 * Retrieves the root associated to the provided name and wrap it in an {@linkplain Optional}.
	 * If the mapping doesn't exist, return an empty {@linkplain Optional} instead.
	 * @param name the local name of the root.
	 * @return the target root
	 */
	public Optional<N> getRoot(I identity) { return Optional.ofNullable(this.roots.get(identity)); }

	/**
	 * Retrieves the root associated to the provided name, and creates a new one if the mapping doesn't exist.
	 * @param name the local name of the root.
	 * @return the target root, or a fresh created one.
	 */
	public N getOrNewRoot(I identity)
	{
		Optional<N> root = this.getRoot(identity);

		if (!root.isPresent())
		{
			N newRoot = this.rootProducer.apply(identity);
			this.roots.put(identity, newRoot);
			return newRoot;
		}

		return root.get();
	}

	/**
	 * Uses {@linkplain Node}'s navigation at root level, enabling usage of full names.
	 * @param identity the target node's full name
	 * @param navigator the navigator as in {@linkplain Node#navigate(String, Function)}
	 * @return the navigation result
	 * @see Node#navigate(String, Function)
	 * @see Node#getFullName()
	 */
	public Optional<N> navigate(I[] path, BiFunction<Node<N, I>, I, Optional<N>> navigator)
	{
		if (path.length > 0)
			return this.getRoot(path[0]).flatMap(root -> root.navigate(path, navigator));

		return Optional.empty();
	}

	public VisitSignal visit(TreeVisitor<? super N, I> visitor)
	{
		for (N root : this.roots.values())
			if (visitor.traverse(root) == VisitSignal.STOP)
				return VisitSignal.STOP;

		return VisitSignal.CONTINUE;
	}

	public VisitSignal visit(TreeVisitor<? super N, I> visitor, I root)
	{
		N node = this.getRoot(root).orElse(null);

		if (node != null)
			return visitor.traverse(node);

		return VisitSignal.DROP;
	}
}
