package fr.az.crispack.util.trees.visit;

import fr.az.crispack.util.trees.Node;

public interface TreeVisitor<N extends Node<N, I>, I>
{
	default VisitSignal traverse(N root)
	{
		return this.traverse(root, 0);
	}

	default VisitSignal traverse(N node, int depth)
	{
		switch (this.visit(node, depth))
		{
			case DROP: return VisitSignal.CONTINUE; //Drop branch, but continue tree visit
			case STOP: return VisitSignal.STOP;		//Stop tree visit
			default:
		}

		for (N child : node.children().values())
			if (this.traverse(child, depth + 1) == VisitSignal.STOP)
				return VisitSignal.STOP;

		return VisitSignal.CONTINUE;
	}

	VisitSignal visit(N node, int depth);
}
