package fr.az.registry.strategy.logging;

public interface LoggingStrategy
{
	void info(String message);
	void warning(String message);
	void error(String message);
	void error(Throwable throwable);
}
