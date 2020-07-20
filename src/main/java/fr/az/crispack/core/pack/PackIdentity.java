package fr.az.crispack.core.pack;

import fr.az.crispack.core.version.Version;

public record PackIdentity(String author, String name, Version version, PackType type)
{

}
