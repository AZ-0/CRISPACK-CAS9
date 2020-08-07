package fr.az.cytokine.domain.resolve.conflict;

import fr.az.cytokine.domain.resolve.flatten.FlatDependency;

public class HandlerSelectByDeclaration implements ConflictHandler
{
	private final boolean chooseFirst;

	public HandlerSelectByDeclaration(boolean chooseFirst)
	{
		this.chooseFirst = chooseFirst;
	}

	@Override
	public FlatDependency solve(FlatDependency registered, FlatDependency concurrent)
	{
		return this.chooseFirst ? registered : concurrent;
	}
}
