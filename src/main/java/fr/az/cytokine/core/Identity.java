package fr.az.cytokine.core;

import fr.az.cytokine.core.pack.PackType;

public interface Identity
{
	String author();
	String name();
	PackType type();
}
