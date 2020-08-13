package fr.az.cytokine.infra.user.cli.argument;

import java.util.HashSet;
import java.util.Set;

import com.beust.jcommander.JCommander;

public abstract class Arguments
{
	private static final Set<Arguments> ARGUMENTS = new HashSet<>();

	public static void init(JCommander.Builder builder)
	{
		ARGUMENTS.forEach(builder::addObject);
	}

	protected Arguments()
	{
		ARGUMENTS.add(this);
	}

	public abstract void reset();
}
