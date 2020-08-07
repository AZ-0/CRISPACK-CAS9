package fr.az.cytokine.json.dependency.read;

import java.util.List;

import fr.az.cytokine.core.dependency.Dependency;
import fr.az.cytokine.core.dependency.context.ReadingContext;
import fr.az.cytokine.core.pack.PackType;
import fr.az.cytokine.core.version.Version;
import fr.az.cytokine.impl.github.GithubDependency;
import fr.az.cytokine.json.Keys;
import fr.az.util.parsing.json.JSONParsingException;
import fr.az.util.parsing.json.keys.structure.Mandatory;
import fr.az.util.parsing.json.keys.structure.Structure;

public class KeyGithubDependency extends ObjectDependencyKey
{
	private static final long serialVersionUID = -8348951049194968239L;

	private static final Mandatory MANDATORY = new Mandatory(Keys.AUTHOR, Keys.NAME, Keys.VERSION, Keys.TYPE);

	public KeyGithubDependency(ReadingContext context)
	{
		super(context);
	}

	@Override
	public GithubDependency build(List<Structure> structures) throws JSONParsingException
	{
		String author	= Keys.AUTHOR	.get(MANDATORY);
		String name		= Keys.NAME		.get(MANDATORY);
		Version version	= Keys.VERSION	.get(MANDATORY);
		PackType type	= Keys.TYPE		.get(MANDATORY);

		return new GithubDependency(author, name, type, version);
	}

	@Override public List<Structure> getStructures() { return List.of(MANDATORY); }
	@Override public Dependency.Kind kind() { return Dependency.Kind.GITHUB; }
}
