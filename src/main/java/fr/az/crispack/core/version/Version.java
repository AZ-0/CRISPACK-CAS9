package fr.az.crispack.core.version;

public record Version(String raw)
{
	@Override public String toString() { return this.raw; }
}
