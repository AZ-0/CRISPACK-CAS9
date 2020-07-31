package fr.az.crispack.json;

import java.util.regex.Pattern;

import fr.az.crispack.core.pack.PackType;
import fr.az.crispack.json.misc.KeyVersion;
import fr.az.util.parsing.json.keys.commons.KeyString;
import fr.az.util.parsing.json.keys.commons.SimpleEnumKey;
import fr.az.util.parsing.json.keys.types.EnumKey;

public class Keys
{
	public static final Pattern ANY_WORD = Pattern.compile("\\w+");

	public static final KeyString NAME		= new KeyString("name",		ANY_WORD);
	public static final KeyString AUTHOR	= new KeyString("author",	ANY_WORD);

	public static final EnumKey<PackType> TYPE = new SimpleEnumKey<>(PackType.values(), "type");

	public static final KeyVersion VERSION = new KeyVersion();
}
