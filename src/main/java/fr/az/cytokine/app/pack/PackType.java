package fr.az.cytokine.app.pack;

public enum PackType
{
	DATAPACK,
	RESOURCESPACK,
	;

	public String dirName() { return this.name().toLowerCase(); }
}
