package fr.az.cytokine.app;

import java.nio.file.Path;
import java.util.Map;

import fr.az.cytokine.app.pack.DataPack;

public record Save(String name, Map<String, DataPack> datapacks, Path dir)
{
}
