package fr.cytokine.server.github;

import java.util.HashMap;
import java.util.Map;

import fr.az.cytokine.api.core.pack.PackType;
import fr.az.cytokine.domain.Version;

class GithubTable
{
	private final Map<Version, GithubTag> tags;
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

	void add(Version tag, String sha, String zipURL)
	{
		this.tags.put(tag, new GithubTag(this, tag, sha, zipURL));
	}

	public GithubTag get(Version tag) { return this.tags.get(tag); }

	public Map<Version, GithubTag> asMap() { return this.tags; }

	public PackType type() { return this.type; }
	public String author() { return this.author; }
	public String repository() { return this.repo; }

	public static record Identity(String author, String repository) {}
}
