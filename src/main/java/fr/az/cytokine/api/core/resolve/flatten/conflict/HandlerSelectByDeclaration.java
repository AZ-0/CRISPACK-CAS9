package fr.az.cytokine.api.core.resolve.flatten.conflict;

import fr.az.cytokine.api.core.resolve.flatten.FlatDependency;

class HandlerSelectByDeclaration implements ConflictHandler
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
