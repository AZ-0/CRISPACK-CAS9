package fr.az.cytokine.infra.server.json.misc;

import fr.az.cytokine.app.version.Version;
import fr.az.util.parsing.json.JSONParsingException;
import fr.az.util.parsing.json.keys.Key;

public class KeyVersion implements Key<String, Version>
{
	private static final long serialVersionUID = 8563443004387096775L;

	@Override
	public Version parse(String from) throws JSONParsingException
	{
		return new Version(from);
	}

	@Override public Class<String> expectedType() { return String.class; }
	@Override public String getKey() { return "version"; }
}