package fr.az.crispack.core.dependency.extract;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.json.JSONObject;

import fr.az.crispack.core.dependency.Dependency;
import fr.az.crispack.core.dependency.context.ZipReadingContext;
import fr.az.crispack.util.Util;

public class ZipDependencyExtractor extends DependencyExtractor
{
	public static ZipDependencyExtractor of(Path file) throws ZipException, IOException
	{
		return new ZipDependencyExtractor(new ZipReadingContext(file));
	}

	public static ZipDependencyExtractor of(Path file, ZipFile zip)
	{
		return new ZipDependencyExtractor(new ZipReadingContext(file, zip));
	}

	public ZipDependencyExtractor(ZipReadingContext context)
	{
		super(context);
	}

	@Override
	public List<Dependency> extract()
	{
		ZipFile zip = this.context().asZip().zip();
		String content = Util.getContent(Util.safeOp(zip.getEntry("pack.mcmeta"), zip::getInputStream));
		return new JSONDependencyExtractor(new JSONObject(content), this.context()).extract();
	}
}
