package fr.az.cytokine.strategy.launching;

import fr.az.cytokine.strategy.process.PromptMain;

public class PromptLaunchingStrategy implements LaunchingStrategy
{
	@Override
	public void launch(String... args)
	{
		if (args.length != 0)
		{
			new PromptMain().run(args);
			System.exit(0);
		}

		System.out.println("I'm ready to serve you master owo");
	}
}
