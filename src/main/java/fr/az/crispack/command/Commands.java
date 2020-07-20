package fr.az.crispack.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.beust.jcommander.JCommander;

public abstract class Commands
{
	private static final Map<String, Commands> COMMANDS = new HashMap<>();

	public static final DisplayCommand	DISPLAY	= new DisplayCommand();
	public static final ExitCommand		EXIT	= new ExitCommand();
	public static final LoadCommand		LOAD	= new LoadCommand();

	public static boolean isRegistered(String name) { return COMMANDS.containsKey(name); }
	public static Optional<Commands> getByName(String name) { return Optional.ofNullable(COMMANDS.get(name)); }

	public static void init(JCommander.Builder builder)
	{
		COMMANDS.forEach((name, cmd) -> builder.addCommand(name, cmd, cmd.getAliases()));
	}

	private final String name;
	private final String[] aliases;

	protected Commands(String name, String... aliases)
	{
		if (COMMANDS.putIfAbsent(name, this) != null)
			System.err.println("Whoaa there, name conflict between commands %s and %s for '%s'".formatted
			(
				COMMANDS.get(name).getClass().getSimpleName(),
				this.getClass().getSimpleName(),
				name
			));

		this.name = name;
		this.aliases = aliases;
	}

	public abstract boolean execute();
	public abstract void reset();

	public String getName() { return this.name; }
	public String[] getAliases() { return this.aliases; }
}
