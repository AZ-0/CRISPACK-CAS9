package fr.az.crispack.command.delegate;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

public class SelectionArgs implements Delegate
{
	@Parameter(names = {"-m", "-map", "-maps"}, variableArity = true, description = "select specific maps for operations")
	private List<String> maps = new ArrayList<>();
	@Parameter(names = {"-dp", "--datapack", "--datapacks"}, variableArity = true, description = "select specific datapacks for operations")
	private List<String> datapacks = new ArrayList<>();

	public boolean hasMaps() { return !this.maps.isEmpty(); }
	public boolean hasDatapacks() { return !this.datapacks.isEmpty(); }

	public List<String> maps() { return this.maps; }
	public List<String> datapacks() { return this.datapacks; }

	@Override
	public void reset()
	{
		this.maps.clear();
		this.datapacks.clear();
	}
}
