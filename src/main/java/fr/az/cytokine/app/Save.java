package fr.az.cytokine.app;

import java.util.Map;

import fr.az.cytokine.app.pack.DataPack;

public interface Save
{
	String name();
	Map<String, DataPack> packs();
}
