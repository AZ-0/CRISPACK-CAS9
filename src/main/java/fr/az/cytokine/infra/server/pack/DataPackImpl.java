package fr.az.cytokine.infra.server.pack;

import java.util.List;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.app.pack.DataPack;
import fr.az.cytokine.app.version.Version;

public record DataPackImpl(String name, String author, Version version, List<Dependency> dependencies) implements DataPack
{

}
