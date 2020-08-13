package fr.az.cytokine.app.pack;

import java.util.List;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.app.version.Version;

public interface DataPack
{
	String author();
	String name();
	Version version();
	List<Dependency> dependencies();

	default PackType type() { return PackType.DATAPACK; }
}
