package fr.az.registry.property;

public enum Header
{
	USER_AGENT("User-Agent", "Datapack Dependency Manager"),
	;

	private final String header;
	private final String value;

	private Header(String header, String value)
	{
		this.header = header;
		this.value = value;
	}

	public String header() { return this.header; }
	public String value()  { return this.value; }
}
