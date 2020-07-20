package fr.az.crispack.core.pack;

import java.util.Optional;

import fr.az.crispack.core.Save;
import fr.az.crispack.core.pack.content.PackContent;
import fr.az.crispack.core.version.Version;

public interface Pack<C extends PackContent>
{
	void writeContent(Save in, C content);
	Optional<C> loadContent(Save in);

	String author();
	String name();
	Version version();
}
