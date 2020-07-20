package fr.az.crispack.core;

import java.nio.file.Path;
import java.util.Map;

import fr.az.crispack.core.pack.DataPack;

public record Save(String name, Map<String, DataPack> datapacks, Path dir)
{
}
