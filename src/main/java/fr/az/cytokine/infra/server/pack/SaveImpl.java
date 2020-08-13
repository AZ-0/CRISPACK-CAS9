package fr.az.cytokine.infra.server.pack;

import java.util.Map;

import fr.az.cytokine.api.core.pack.DataPack;
import fr.az.cytokine.api.core.pack.Save;

public record SaveImpl(String name, Map<String, DataPack> packs) implements Save
{
}
