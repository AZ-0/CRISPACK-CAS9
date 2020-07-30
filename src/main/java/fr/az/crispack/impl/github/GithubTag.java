package fr.az.crispack.impl.github;

import java.nio.file.Path;

import fr.az.crispack.core.dependency.Dependency;

import reactor.core.publisher.Flux;

public class GithubTag
{
	private final String tag;
	private final String sha;
	private final String url;

	private Path file;

	public GithubTag(String tag, String sha, String url)
	{
		this.tag = tag;
		this.sha = sha;
		this.url = url;
	}

	public Flux<Dependency> getDependencies()
	{
		return Flux.empty();
	}

	public String tag() { return this.tag; }
	public String sha() { return this.sha;  }
	public String url() { return this.url;  }
	public Path file() { return this.file; }

	public void setFile(Path file) { this.file = file; }

	@Override
	public boolean equals(Object to)
	{
		return to instanceof GithubTag tag
			&& tag.tag.equals(this.tag)
			&& tag.sha.equals(this.sha);
	}

	@Override public int hashCode() { return this.tag.hashCode() + this.sha.hashCode(); }
}
