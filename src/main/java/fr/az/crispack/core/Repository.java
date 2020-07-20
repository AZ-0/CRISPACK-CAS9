package fr.az.crispack.core;

import java.util.Optional;

import fr.az.crispack.core.pack.DataPack;
import fr.az.crispack.core.pack.Pack;
import fr.az.crispack.core.pack.PackIdentity;
import fr.az.crispack.core.pack.ResourcesPack;

public interface Repository
{
	boolean has(PackIdentity descriptor);
	Optional<Pack<?>> resolve(PackIdentity descriptor);
	Optional<DataPack> resolveDataPack(PackIdentity descriptor);
	Optional<ResourcesPack> resolveResourcesPack(PackIdentity descriptor);
}
