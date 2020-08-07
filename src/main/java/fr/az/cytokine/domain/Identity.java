package fr.az.cytokine.domain;

import fr.az.cytokine.domain.pack.PackType;

public interface Identity
{
	String author();
	String name();
	PackType type();
}
