package fr.az.crispack.command;

import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Exit session")
public class ExitCommand extends Commands
{
	protected ExitCommand()
	{
		super("exit", "quit", "stop");
		Runtime.getRuntime().addShutdownHook(new Thread(() -> Commands.EXIT.clean()));
	}

	@Override
	public boolean execute()
	{
		System.exit(0);
		return true;
	}

	@Override public void reset() {}

	private void clean()
	{

	}
}
