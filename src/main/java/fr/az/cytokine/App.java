package fr.az.cytokine;

import java.net.http.HttpClient;

import fr.az.cytokine.server.property.Properties;

public class App
{
	private final static App APP = new App();

	public static App app() { return APP; }

	public static HttpClient http() { return App.APP.getHttpClient(); }

	public static void main(String... args)
	{
		Properties.init();
	}

	private final HttpClient http;

	public App()
	{
		this.http = HttpClient.newBuilder().build();
	}

	public HttpClient getHttpClient() { return this.http; }
}
