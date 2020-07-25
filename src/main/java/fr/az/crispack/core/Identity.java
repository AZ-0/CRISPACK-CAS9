package fr.az.crispack.core;

import fr.az.crispack.core.pack.PackType;

public interface Identity
{
	String author();
	String name();
	PackType type();
}
