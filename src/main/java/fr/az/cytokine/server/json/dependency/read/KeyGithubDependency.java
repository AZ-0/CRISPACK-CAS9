package fr.az.cytokine.server.json.dependency.read;

import java.util.List;

import fr.az.cytokine.domain.pack.PackType;
import fr.az.cytokine.domain.version.Version;
import fr.az.cytokine.server.dependency.context.ReadingContext;
import fr.az.cytokine.server.github.GithubDependency;
import fr.az.cytokine.server.json.Keys;
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
	@Override public String getKey() { return "github"; }
}