package fr.az.crispack.core.version.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import fr.az.crispack.core.version.Version;
import fr.az.crispack.core.version.Versions;

public class VersionList implements VersionTable
{
	private static final long serialVersionUID = 1618471848337430489L;

	private final List<Version> versions;

	public VersionList(Version... versions)
	{
		this.versions = Arrays.asList(versions);
	}

	public VersionList(Collection<? extends Version> versions)
	{
		this.versions = new ArrayList<>(versions);
	}

	@Override public Version latest() { return this.versions.get(0); }

	@Override
	public Optional<Version> latest(Versions bounds)
	{
		for (Version version : this.versions)
			if (bounds.has(version))
				return Optional.of(version);

		return Optional.empty();
	}

	@Override public void register(Version version) { this.versions.add(version); }
}
