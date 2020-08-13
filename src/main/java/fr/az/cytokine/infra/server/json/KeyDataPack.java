package fr.az.cytokine.infra.server.json;

import java.util.List;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.app.pack.DataPack;
import fr.az.cytokine.app.version.Version;
import fr.az.cytokine.infra.server.pack.DataPackImpl;
import fr.az.util.parsing.json.JSONParsingException;
import fr.az.util.parsing.json.keys.structure.Mandatory;
import fr.az.util.parsing.json.keys.structure.Optional;
import fr.az.util.parsing.json.keys.structure.Structure;
import fr.az.util.parsing.json.keys.types.RootKey;

public class KeyDataPack implements RootKey<DataPack>
{
	private static final long serialVersionUID = 3918381136263197596L;

	private static final Mandatory MANDATORY = new Mandatory(Keys.PACK_FORMAT, Keys.VERSION);
	private static final Optional OPTIONAL = new Optional(Keys.NAME, Keys.AUTHOR, Keys.DESCRIPTION, Keys.DEPENDENCIES);

	@Override
	public DataPack build(List<Structure> structures) throws JSONParsingException
	{
		String name		= Keys.NAME.get(MANDATORY);
		String author	= Keys.AUTHOR.get(MANDATORY);
		Version version	= Keys.VERSION.get(MANDATORY);
		List<Dependency> dependencies = Keys.DEPENDENCIES.opt(OPTIONAL, List::of);

		return new DataPackImpl(author, name, version, dependencies);
	}

	@Override public List<Structure> getStructures() { return List.of(MANDATORY, OPTIONAL); }
	@Override public String getKey() { return "pack"; }

	@Override
	public void onError(JSONParsingException e, String file)
	{
		System.err.println("Could not load datapack: "+ e.getMessage());
	}
}
