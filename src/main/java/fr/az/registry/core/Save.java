package fr.az.registry.core;

import java.nio.file.Path;
import java.util.Map;

import fr.az.registry.core.pack.DataPack;

public record Save(String name, Map<String, DataPack> datapacks, Path dir)
{
}
