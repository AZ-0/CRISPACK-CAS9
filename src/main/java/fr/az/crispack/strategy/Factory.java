package fr.az.crispack.strategy;

import java.util.function.Consumer;

import fr.az.crispack.App;
import fr.az.crispack.strategy.launching.LaunchingStrategy;
import fr.az.crispack.strategy.launching.PromptLaunchingStrategy;
import fr.az.crispack.strategy.logging.LoggingStrategy;
import fr.az.crispack.strategy.logging.PromptLoggingStrategy;
import fr.az.crispack.strategy.process.PromptMain;

public class Factory
{
	public LoggingStrategy getLoggingStrategy(Mode mode)
	{
		return switch (mode)
		{
			case CLI ->	new PromptLoggingStrategy();
			default  ->	this.exitOnDefault("logging", mode, System.err::println);
		};
	}

	public LaunchingStrategy getLaunchingStrategy(Mode mode)
	{
		return switch (mode)
		{
			case CLI ->	new PromptLaunchingStrategy();
			default  ->	this.exitOnDefault("launching", mode, System.err::println);
		};
	}

	public Runnable getMainProcess(Mode mode)
	{
		return switch (mode)
		{
			case CLI -> new PromptMain();
			default  -> this.exitOnDefault("running", mode, App.logger()::error);
		};
	}

	private <T> T exitOnDefault(String factoryMethod, Mode mode, Consumer<String> logger) {
		return this.exitOnDefault(factoryMethod, mode, 1, logger); }

	private <T> T exitOnDefault(String factoryMethod, Mode mode, int status, Consumer<String> logger)
	{
		logger.accept("No %s strategy implemented for mode: %s".formatted(factoryMethod, mode));
		System.exit(status);
		return null;
	}
}
