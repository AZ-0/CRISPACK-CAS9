package fr.az.crispack.core.load.meta;

import java.util.Set;

import fr.az.crispack.core.Dependency;
import fr.az.crispack.core.pack.PackType;
import fr.az.crispack.core.version.Version;

public record McMeta(PackType type, String name, String author, Version version, Set<String> tags, Set<Dependency> dependencies)
{

}
