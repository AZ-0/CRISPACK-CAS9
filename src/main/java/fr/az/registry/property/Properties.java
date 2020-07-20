package fr.az.registry.property;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import fr.az.registry.App;

public class Properties
{
	private static final String APP_NAME = "DPDM";

	private static OS OPERATING_SYSTEM;
	private static Path MC_FOLDER;
	private static Path SAVES_FOLDER;
	private static Path PROGRAM_FOLDER;
	private static Path DATAPACKS_FOLDER;
	private static Path RESOURCEPACKS_FOLDER;

	public static String appName() { return APP_NAME; }
	public static OS os() { return OPERATING_SYSTEM; }
	public static Path minecraftFolder() { return MC_FOLDER; }
	public static Path savesFolder() { return SAVES_FOLDER; }
	public static Path programFolder() { return PROGRAM_FOLDER; }
	public static Path datapacksFolder() { return DATAPACKS_FOLDER; }
	public static Path resourcepacksFolder() { return RESOURCEPACKS_FOLDER; }

	public static void init()
	{
		Properties.initOs();
		Properties.initMinecraftFolder();
	}

	public static void initOs()
	{
		OPERATING_SYSTEM = OS.get();

		if (OPERATING_SYSTEM == null)
		{
			App.logger().error(String.format
			(
				"A wild operating system appeared!\n'%s' isn't in the osdex...\nTry with one of these instead: %s",
				System.getProperty("os.name"),
				Arrays.toString(fr.az.registry.property.OS.values())
			));

			System.exit(0);
		}
	}

	public static void initMinecraftFolder()
	{
		MC_FOLDER = Path.of(Properties.os().getMinecraftPath());

		if (Files.notExists(MC_FOLDER) || !Files.isDirectory(MC_FOLDER))
		{
			App.logger().error("Uh Oh... Looks like minecraft isn't installed on your machine!\nYou should play it, it's a nice game :D");
			System.exit(0);
		}

		SAVES_FOLDER = MC_FOLDER.resolve("saves");
		if (Files.notExists(SAVES_FOLDER) || !Files.isDirectory(SAVES_FOLDER))
		{
			App.logger().error("You are lacking... ZA WARUDO! (seriously, there's no map on your computer)");
			System.exit(0);
		}
	}

	public static void initProgramFolder() throws IOException
	{
		PROGRAM_FOLDER = Path.of(Properties.os().getWorkingDir(), Properties.appName()).toRealPath();
		DATAPACKS_FOLDER = PROGRAM_FOLDER.resolve("datapacks");
		RESOURCEPACKS_FOLDER = PROGRAM_FOLDER.resolve("resourcepacks");

		Files.createFile(PROGRAM_FOLDER);
		Files.createFile(DATAPACKS_FOLDER);
		Files.createFile(RESOURCEPACKS_FOLDER);
	}
}
