package fr.az.crispack.property;

public enum Header
{
	USER_AGENT("User-Agent", "CRISPACK-CAS9"),
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
