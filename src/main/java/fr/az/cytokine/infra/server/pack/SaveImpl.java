package fr.az.cytokine.infra.server.pack;

import java.util.Map;

import fr.az.cytokine.app.Save;
import fr.az.cytokine.app.pack.DataPack;

public record SaveImpl(String name, Map<String, DataPack> packs) implements Save
{
}
