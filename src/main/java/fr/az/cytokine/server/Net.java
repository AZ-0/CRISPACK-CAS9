package fr.az.cytokine.server;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import fr.az.cytokine.server.property.Properties;

public class Net
{
	private final static HttpClient http;

	static
	{
		http = HttpClient.newBuilder().build();
	}

	public static HttpClient http() { return http; }

	public static HttpRequest.Builder request(String uri) {
		return request(URI.create(uri)); }

	public static HttpRequest.Builder request(URI uri) {
		return request().uri(uri); }

	public static HttpRequest.Builder request()
	{
		HttpRequest.Builder builder = HttpRequest.newBuilder();

		for (Headers header  : Headers.values())
			builder.header(header.header(), header.value());

		return builder;
	}

	public enum Headers
	{
		USER_AGENT("User-Agent", Properties.APP_NAME)
		;

		private final String header;
		private final String value;

		private Headers(String header, String value)
		{
			this.header = header;
			this.value = value;
		}

		public String header() { return this.header; }
		public String value() { return this.value; }
	}
}
