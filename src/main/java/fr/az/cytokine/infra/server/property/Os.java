package fr.az.cytokine.infra.server.property;

import java.io.File;

public enum Os
{
	WINDOWS	(System.getenv("AppData"),			".minecraft"),
	UNIX	(System.getProperty("user.home"),	".minecraft"),
	MAC		(System.getProperty("user.home"),	"Library/Application Support/minecraft")
	;

	public static Os get()
	{
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("win"))
			return WINDOWS;

		if (os.contains("mac"))
			return MAC;

		if (os.contains("nix") || os.contains("nux") || os.contains("aix"))
			return UNIX;

		return null;
	}

	private final String workingDir;
	private final String mcPath;

	private Os(String workingDir, String mcPath)
	{
		this.workingDir = workingDir;
		this.mcPath = mcPath;
	}

	public String getAppDir() { return this.workingDir; }
	public String getMinecraftPath() { return this.workingDir + File.separatorChar + this.mcPath; }

	@Override public String toString() { return this.name().toLowerCase(); }
}
