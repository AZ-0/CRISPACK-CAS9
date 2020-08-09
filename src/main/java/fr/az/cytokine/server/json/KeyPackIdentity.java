package fr.az.cytokine.server.json;

import java.util.List;

import fr.az.cytokine.app.pack.PackIdentity;
import fr.az.cytokine.app.pack.PackType;
import fr.az.cytokine.app.version.Version;
import fr.az.util.parsing.json.JSONParsingException;
import fr.az.util.parsing.json.keys.structure.Mandatory;
import fr.az.util.parsing.json.keys.structure.Optional;
import fr.az.util.parsing.json.keys.structure.Structure;
import fr.az.util.parsing.json.keys.types.ObjectKey;

public class KeyPackIdentity implements ObjectKey<PackIdentity>
{
	private static final long serialVersionUID = 3918381136263197596L;

	private static final Mandatory	MANDATORY	= new Mandatory(Keys.PACK_FORMAT, Keys.NAME, Keys.AUTHOR, Keys.VERSION);
	private static final Optional	OPTIONAL	= new Optional(Keys.DESCRIPTION);

	@Override
	public PackIdentity build(List<Structure> structures) throws JSONParsingException
	{
		String name		= Keys.NAME.get(MANDATORY);
		String author	= Keys.AUTHOR.get(MANDATORY);
		Version version	= Keys.VERSION.get(MANDATORY);

		return new PackIdentity(author, name, version, PackType.DATAPACK);
	}

	@Override public List<Structure> getStructures() { return List.of(MANDATORY, OPTIONAL); }
	@Override public String getKey() { return "pack"; }
}
