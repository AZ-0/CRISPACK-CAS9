package fr.az.registry.core.version.table;

import java.io.Serializable;
import java.util.Optional;

import fr.az.registry.core.version.Version;
import fr.az.registry.core.version.Versions;

public interface VersionTable extends Serializable
{
	Version latest();
	Optional<Version> latest(Versions bounds);

	void register(Version version);
}
