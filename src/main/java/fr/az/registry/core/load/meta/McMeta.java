package fr.az.registry.core.load.meta;

import java.util.Set;

import fr.az.registry.core.Dependency;
import fr.az.registry.core.pack.PackType;
import fr.az.registry.core.version.Version;

public record McMeta(PackType type, String name, String author, Version version, Set<String> tags, Set<Dependency> dependencies)
{

}
