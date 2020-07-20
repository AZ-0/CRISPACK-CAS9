package fr.az.crispack.core.version.table;

import java.io.Serializable;
import java.util.Optional;

import fr.az.crispack.core.version.Version;
import fr.az.crispack.core.version.Versions;

public interface VersionTable extends Serializable
{
	Version latest();
	Optional<Version> latest(Versions bounds);

	void register(Version version);
}
