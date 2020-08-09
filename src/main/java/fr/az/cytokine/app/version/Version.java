package fr.az.cytokine.app.version;

public record Version(String raw)
{
	@Override public String toString() { return this.raw; }
}
