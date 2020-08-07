package fr.az.cytokine.core;

import java.nio.file.Path;
import java.util.Map;

import fr.az.cytokine.core.pack.DataPack;

public record Save(String name, Map<String, DataPack> datapacks, Path dir)
{
}
