package fr.az.registry.strategy.logging;

public class PromptLoggingStrategy implements LoggingStrategy
{
	@Override
	public void info(String message)
	{
		System.out.println(message);
	}

	@Override
	public void warning(String message)
	{
		System.err.println("[WARN] "+ message);
	}

	@Override
	public void error(String message)
	{
		System.err.println("[ERROR] "+ message);
	}

	@Override
	public void error(Throwable throwable)
	{
		throwable.printStackTrace();
	}
}
