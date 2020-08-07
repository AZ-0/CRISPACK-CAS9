package fr.az.cytokine.domain.resolve.conflict;

import fr.az.cytokine.domain.resolve.flatten.FlatDependency;

public class VersionConflictException extends Exception
{
	private static final long serialVersionUID = 8427879841992585844L;

	private final FlatDependency registered;
	private final FlatDependency concurrent;

	public VersionConflictException(FlatDependency registered, FlatDependency concurrent, String message)
	{
		super(message);

		this.registered = registered;
		this.concurrent = concurrent;
	}

	public FlatDependency registered() { return this.registered; }
	public FlatDependency concurrent() { return this.concurrent; }
}
