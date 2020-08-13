package fr.az.cytokine.infra.server.pack;

import java.util.List;

import fr.az.cytokine.api.core.dependency.Dependency;
import fr.az.cytokine.api.core.pack.DataPack;
import fr.az.cytokine.api.core.version.Version;

public record DataPackImpl(String name, String author, Version version, List<Dependency> dependencies) implements DataPack
{

}
