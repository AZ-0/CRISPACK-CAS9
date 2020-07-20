package fr.az.registry.core.version;

public class InvalidVersionException extends RuntimeException
{
	private static final long serialVersionUID = -6714753120385586219L;

	private final String raw;

	public InvalidVersionException(String rawVersion, String message)
	{
		super(message);
		this.raw = rawVersion;
	}

	public String getRaw() { return this.raw; }
}
