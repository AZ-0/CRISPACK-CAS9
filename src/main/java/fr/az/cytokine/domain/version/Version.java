package fr.az.cytokine.domain.version;

public record Version(String raw)
{
	@Override public String toString() { return this.raw; }
}
