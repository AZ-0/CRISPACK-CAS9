package fr.cytokine.server.json;

import java.util.regex.Pattern;

import fr.az.cytokine.api.core.pack.PackType;
import fr.cytokine.server.json.dependency.KeyDependencies;
import fr.cytokine.server.json.misc.KeyVersion;
import fr.az.util.parsing.json.keys.commons.KeyByte;
import fr.az.util.parsing.json.keys.commons.KeyString;
import fr.az.util.parsing.json.keys.commons.SimpleEnumKey;
import fr.az.util.parsing.json.keys.types.EnumKey;

public class Keys
{
	public static final Pattern ANY_WORD = Pattern.compile("\\w+");

	public static final KeyByte PACK_FORMAT = new KeyByte((byte) 0);

	public static final KeyString AUTHOR		= new KeyString("author",	ANY_WORD);
	public static final KeyString DESCRIPTION	= new KeyString("description");
	public static final KeyString NAME			= new KeyString("name",		ANY_WORD);

	public static final EnumKey<PackType> TYPE = new SimpleEnumKey<>(PackType.values(), "type");

	public static final KeyVersion VERSION = new KeyVersion();
	public static final KeyDependencies DEPENDENCIES = new KeyDependencies();
	public static final KeyDataPack DATAPACK = new KeyDataPack();
}
