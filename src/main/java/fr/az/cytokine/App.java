package fr.az.cytokine;

import java.net.http.HttpClient;

import fr.az.cytokine.property.Properties;
import fr.az.cytokine.strategy.Factory;
import fr.az.cytokine.strategy.Mode;
import fr.az.cytokine.strategy.logging.LoggingStrategy;

public class App
{
	private final static Factory FACTORY = new Factory();
	private final static Mode MODE = Mode.CLI;
	private final static App APP = new App();

	public static Factory factory() { return FACTORY; }
	public static Mode mode() { return MODE; }
	public static App app() { return APP; }

	public static LoggingStrategy logger() { return App.APP.getLogger(); }
	public static HttpClient http() { return App.APP.getHttpClient(); }

	public static void main(String... args)
	{
		FACTORY.getLaunchingStrategy(MODE).launch(args);
		Properties.init();
		FACTORY.getMainProcess(MODE).run();
	}

	private final LoggingStrategy logger;
	private final HttpClient http;

	public App()
	{
		this.logger = FACTORY.getLoggingStrategy(MODE);
		this.http = HttpClient.newBuilder().build();
	}

	public LoggingStrategy getLogger() { return this.logger; };
	public HttpClient getHttpClient() { return this.http; }
}
