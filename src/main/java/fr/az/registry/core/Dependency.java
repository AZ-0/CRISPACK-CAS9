package fr.az.registry.core;

import fr.az.registry.core.pack.PackIdentity;
import fr.az.registry.core.version.Versions;

public record Dependency(Versions target, Repository repository, PackIdentity identifier)
{
}
