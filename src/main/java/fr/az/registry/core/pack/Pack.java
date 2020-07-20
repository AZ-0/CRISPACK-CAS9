package fr.az.registry.core.pack;

import java.util.Optional;

import fr.az.registry.core.Save;
import fr.az.registry.core.pack.content.PackContent;
import fr.az.registry.core.version.Version;

public interface Pack<C extends PackContent>
{
	void writeContent(Save in, C content);
	Optional<C> loadContent(Save in);

	String author();
	String name();
	Version version();
}
