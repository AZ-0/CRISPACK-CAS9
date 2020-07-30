package fr.az.crispack.util.trees.visit;

public enum VisitSignal
{
	STOP,
	CONTINUE,
	DROP,
	;

	@Override public String toString() { return this.name().toLowerCase().replace('_', ' '); }
}
