package fr.az.registry.core;

import java.util.Optional;

import fr.az.registry.core.pack.DataPack;
import fr.az.registry.core.pack.Pack;
import fr.az.registry.core.pack.PackIdentity;
import fr.az.registry.core.pack.ResourcesPack;

public interface Repository
{
	boolean has(PackIdentity descriptor);
	Optional<Pack<?>> resolve(PackIdentity descriptor);
	Optional<DataPack> resolveDataPack(PackIdentity descriptor);
	Optional<ResourcesPack> resolveResourcesPack(PackIdentity descriptor);
}
