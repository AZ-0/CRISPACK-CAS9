package fr.az.cytokine.server.property;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import fr.az.cytokine.domain.pack.PackType;

public class Properties
{
	public static final String APP_NAME = "CRISPACK-CAS9";
	public static final String SAVE_DATAPACK_FOLDER = "datapacks";
	public static final String ZIP_CACHE_FILE_NAME = "content.zip";

	private static Os OS;
	private static Path MC_FOLDER;
	private static Path SAVES_FOLDER;
	private static Path APP_FOLDER;

	public static Os os() { return OS; }
	public static Path minecraftFolder() { return MC_FOLDER; }
	public static Path savesFolder() { return SAVES_FOLDER; }
	public static Path appFolder() { return APP_FOLDER; }

	// --------------
	// | TINY LOGIC |
	// --------------

	public static Path getAppPath(PackType type) { return APP_FOLDER.resolve(type.dirName()); }
	public static Path getAppPath(PackType type, String first, String... more)
	{
		return getAppPath(type).resolve(Path.of(first, more));
	}

	public static Path getSaveFolder(String save) { return SAVES_FOLDER.resolve(save); }

	public static Path getDatapackFolder(Path save) { return save.resolve(SAVE_DATAPACK_FOLDER); }
	public static Path getDatapackFolder(String save) { return getDatapackFolder(getSaveFolder(save)); }

//	public static Stream<Path> getSaves() { return Util.listDirs(savesFolder()); }
//	public static Stream<Path> getDatapacks() { return getSaves().flatMap(Properties::getDatapacks); }

//	public static Stream<Path> getDatapacks(Path save)   { return Util.listDirs(getDatapackFolder(save)); }
//	public static Stream<Path> getDatapacks(String save) { return Util.listDirs(getDatapackFolder(save)); }

	// ------------------
	// | INITIALIZATION |
	// ------------------

	public static void init()
	{
		Properties.initOs();
		Properties.initMinecraftFolder();
//		Util.safeOp(os().getAppDir(), Properties::initProgramFolder);
	}

	private static void initOs()
	{
		OS = Os.get();

		if (OS == null)
/*			App.logger().error("A wild operating system appeared! '%s' isn't in the osdex...\nTry with one of these instead: %s".formatted
			(
				System.getProperty("os.name"),
				Arrays.toString(Os.values()).toLowerCase()
			));
*/
			System.exit(1);
	}

	private static void initMinecraftFolder()
	{
		MC_FOLDER = Path.of(os().getMinecraftPath());

		if (!Files.isDirectory(MC_FOLDER))
//			App.logger().error("Uh Oh... Looks like minecraft isn't installed on your machine!\nYou should play it, it's a nice game :D");
			System.exit(1);

		SAVES_FOLDER = MC_FOLDER.resolve("saves");
		if (!Files.isDirectory(SAVES_FOLDER))
//			App.logger().error("Where has the world gone? There's no map in your minecraft folder...");
			System.exit(1);
	}

	public static Void initProgramFolder(String workingDir) throws IOException
	{
		APP_FOLDER = Path.of(os().getAppDir(), APP_NAME).toRealPath();
		Files.createDirectories(APP_FOLDER);

		for (PackType type : PackType.values())
			Files.createDirectories(getAppPath(type));

		return null;
	}
}
