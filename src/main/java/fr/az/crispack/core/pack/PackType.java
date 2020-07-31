package fr.az.crispack.core.pack;

public enum PackType
{
	DATAPACK,
	RESOURCESPACK,
	;

	public String dirName() { return this.name().toLowerCase(); }
}
