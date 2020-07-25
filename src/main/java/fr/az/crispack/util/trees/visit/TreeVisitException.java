package fr.az.crispack.util.trees.visit;

public class TreeVisitException extends Exception
{
	private static final long serialVersionUID = 6242734474352040454L;

	private final VisitSignal behavior;

	public TreeVisitException(VisitSignal behavior)
	{
		this.behavior = behavior;
	}

	public TreeVisitException(String message, VisitSignal behavior)
	{
		super(message);
		this.behavior = behavior;
	}

	public VisitSignal behavior() { return this.behavior; }
}
