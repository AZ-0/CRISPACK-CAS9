package fr.cytokine.server.pack;

import fr.az.cytokine.api.core.pack.DataPack;
import fr.az.cytokine.api.core.version.Version;
import fr.az.cytokine.domain.dependency.Dependency;

import java.util.List;

public record DataPackImpl(String name, String author, Version version, List<Dependency> dependencies) implements DataPack
{

}
