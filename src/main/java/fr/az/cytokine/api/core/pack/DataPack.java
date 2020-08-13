package fr.az.cytokine.api.core.pack;

import java.util.List;

import fr.az.cytokine.api.core.dependency.Dependency;
import fr.az.cytokine.api.core.version.Version;

public interface DataPack
{
	String author();
	String name();
	Version version();
	List<Dependency> dependencies();

	default PackType type() { return PackType.DATAPACK; }
}
