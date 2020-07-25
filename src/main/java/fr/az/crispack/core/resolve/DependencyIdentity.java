package fr.az.crispack.core.resolve;

import fr.az.crispack.core.Identity;
import fr.az.crispack.core.Repository;
import fr.az.crispack.core.pack.PackType;

public record DependencyIdentity(Repository repository, String author, String name, PackType type) implements Identity
{
	public boolean equals(DependencyIdentity other)
	{
		if (other == null)
			return false;

		return other.author	.equals(this.author)
			&& other.name	.equals(this.name)
			&& other.type	.equals(this.type);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof DependencyIdentity identity))
			return false;

		return this.equals(identity);
	}
}
