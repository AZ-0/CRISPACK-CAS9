package fr.az.crispack.impl.github;

import java.util.HashMap;
import java.util.Map;

import fr.az.crispack.core.pack.PackType;

class GithubTable
{
	private final Map<String, GithubTag> tags;
	private final PackType type;
	private final String author;
	private final String repo;

	public GithubTable(PackType type, String author, String repository)
	{
		this.tags = new HashMap<>();
		this.type = type;
		this.author = author;
		this.repo = repository;
	}

	public void add(String tag, String sha, String zipURL)
	{
		this.tags.put(tag, new GithubTag(this, tag, sha, zipURL));
	}

	public GithubTag get(String name) { return this.tags.get(name); }

	public Map<String, GithubTag> asMap() { return this.tags; }

	public PackType type() { return this.type; }
	public String author() { return this.author; }
	public String repository() { return this.repo; }

	public static record Identity(String author, String repository) {}
}
