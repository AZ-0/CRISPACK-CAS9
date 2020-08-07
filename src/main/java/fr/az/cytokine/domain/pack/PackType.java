package fr.az.cytokine.domain.pack;

public enum PackType
{
	DATAPACK,
	RESOURCESPACK,
	;

	public String dirName() { return this.name().toLowerCase(); }
}
