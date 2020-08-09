package fr.az.cytokine;

import fr.az.cytokine.server.property.Properties;

public class App
{
	private final static App APP = new App();

	public static App app() { return APP; }

	public static void main(String... args)
	{
		Properties.init();
	}
}
