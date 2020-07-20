package fr.az.registry.core.pack;

import fr.az.registry.core.version.Version;

public record PackIdentity(String author, String name, Version version, PackType type)
{

}
