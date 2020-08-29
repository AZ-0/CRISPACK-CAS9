package fr.cytokine.server.json.misc;

import fr.az.cytokine.api.core.version.Version;
import fr.az.util.parsing.json.keys.Key;

public class KeyVersion implements Key<String, Version>
{
	private static final long serialVersionUID = 8563443004387096775L;

	@Override
	public Version parse(String from)
	{
		return new Version(from);
	}

	@Override public Class<String> expectedType() { return String.class; }
	@Override public String getKey() { return "raw"; }
}
