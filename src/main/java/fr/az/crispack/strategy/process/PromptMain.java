package fr.az.crispack.strategy.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import fr.az.crispack.App;
import fr.az.crispack.command.Commands;
import fr.az.crispack.command.argument.Arguments;

public class PromptMain implements Runnable
{
	private static final Pattern ARG_PARSER = Pattern.compile("\"([^\"]*)\"|(\\S+)");

	private boolean running;
	private JCommander jcmd;

	public PromptMain()
	{
		JCommander.Builder builder = JCommander.newBuilder()
				.programName("Dependency Manager");

		Arguments.init(builder);
		Commands.init(builder);

		this.jcmd = builder.build();
	}

	@Override
	public void run()
	{
		this.running = true;
		Scanner sc = new Scanner(System.in);

		while (this.running)
		{
			System.out.print("> ");
			String raw = sc.nextLine();

			if (!this.run(this.asArgs(raw)))
				continue;
		}

		sc.close();
	}

	public boolean run(String... args)
	{
		try
		{
			this.jcmd.parse(args);
		} catch(ParameterException e)
		{
			App.logger().info(e.getMessage() + '\n');
			e.getJCommander().usage();
			return false;
		}

		String label = this.jcmd.getParsedCommand();
		Commands cmd = Commands.getByName(label).orElseThrow(); //JCommander validates command existance beforehand

		try
		{
			boolean success = cmd.execute();
			cmd.reset();
			return success;
		} catch (Throwable t)
		{
			App.logger().error(t);
			return false;
		}
	}

	private String[] asArgs(String line)
	{
		List<String> args = new ArrayList<>();
		Matcher matcher = ARG_PARSER.matcher(line);

		while (matcher.find())
			args.add(matcher.group());

		return args.toArray(String[]::new);
	}

	public void cancel() { this.running = false; }

	public boolean isRunning() { return this.running; }
	public JCommander jcmd() { return this.jcmd; }
}
