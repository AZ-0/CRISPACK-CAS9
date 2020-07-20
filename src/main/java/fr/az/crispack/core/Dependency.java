package fr.az.crispack.core;

import fr.az.crispack.core.pack.PackIdentity;
import fr.az.crispack.core.version.Versions;

public record Dependency(Versions target, Repository repository, PackIdentity identifier)
{
}
