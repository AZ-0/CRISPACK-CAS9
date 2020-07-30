package fr.az.crispack.impl.github;

import java.util.HashMap;
import java.util.Map;

class GithubTable
{
	private final Map<String, GithubTag> tags = new HashMap<>();

	public void add(GithubTag tag) { this.tags.put(tag.tag(), tag); }

	public GithubTag get(String name) { return this.tags.get(name); }

	public Map<String, GithubTag> asMap() { return this.tags; }

	public static record Identity(String author, String repository) {}
}
