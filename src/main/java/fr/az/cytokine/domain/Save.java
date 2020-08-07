package fr.az.cytokine.domain;

import java.nio.file.Path;
import java.util.Map;

import fr.az.cytokine.domain.pack.DataPack;

public record Save(String name, Map<String, DataPack> datapacks, Path dir)
{
}
