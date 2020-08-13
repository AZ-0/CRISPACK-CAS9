package fr.az.cytokine.infra.server.github;

import static fr.az.cytokine.infra.server.property.Properties.ZIP_CACHE_FILE_NAME;
import static fr.az.cytokine.infra.server.property.Properties.getAppPath;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

import fr.az.cytokine.app.dependency.Dependency;
import fr.az.cytokine.infra.server.Net;
import fr.az.cytokine.infra.server.dependency.extract.DependencyExtractorFactoryImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GithubTag
{
	private final GithubTable table;
	private final String tag;
	private final String sha;
	private final String url;

	private Path file;

	public GithubTag(GithubTable table, String tag, String sha, String url)
	{
		this.table = table;
		this.tag = tag;
		this.sha = sha;
		this.url = url;
	}

	public Flux<Dependency> getDependencies()
	{
		return this
				.getFile()
				.map(path -> new DependencyExtractorFactoryImpl().get(path))
				.flatMap(extractor -> Mono.fromCallable(extractor::extract))
				.flatMapIterable(Function.identity());
	}

	private Mono<Path> getFile()
	{
		if (this.file != null)
			return Mono.just(this.file);

		this.file = getAppPath(this.table.type(), "git", this.table.author(), this.table.repository(), this.tag, ZIP_CACHE_FILE_NAME);

		if (Files.isRegularFile(this.file))
			return Mono.just(this.file);

		return this.download(this.file.getParent()).flatMap(path -> Mono.fromCallable(() -> Files.move(path, this.file)));
	}

	private Mono<Path> download(Path to)
	{
		HttpRequest request = Net.request(this.url).build();

		return Mono
				.fromFuture(() -> Net.http().sendAsync(request, HttpResponse.BodyHandlers.ofFileDownload(to)))
				.map(HttpResponse::body);
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
