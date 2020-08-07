package fr.az.cytokine.util;

import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Function;

public class StringSubscriber<T> implements java.util.concurrent.Flow.Subscriber<CharSequence>
{
	private final StringBuilder builder = new StringBuilder();
	private final Function<? super StringBuilder, ? extends T> mapper;
	private T result;

	public static <T> BodyHandler<T> handle(Function<? super StringBuilder, T> mapper)
	{
		return HttpResponse.BodyHandlers.fromLineSubscriber
		(
			StringSubscriber.of(mapper),
			StringSubscriber::result,
			"\n"
		);
	}

	public static <T> BodyHandler<T> handleString(Function<? super String, T> mapper)
	{
		return HttpResponse.BodyHandlers.fromLineSubscriber
		(
			StringSubscriber.ofString(mapper),
			StringSubscriber::result,
			"\n"
		);
	}

	public static <T> StringSubscriber<T> ofString(Function<? super String, T> mapper)
	{
		Function<StringBuilder, String> source = StringBuilder::toString;
		return new StringSubscriber<>(source.andThen(mapper));
	}

	public static <T> StringSubscriber<T> of(Function<? super StringBuilder, ? extends T> mapper)
	{
		return new StringSubscriber<>(mapper);
	}

	private StringSubscriber(Function<? super StringBuilder, ? extends T> mapper)
	{
		this.mapper = mapper;
	}

	@Override public void onSubscribe(Subscription subscription) {}
	@Override public synchronized void onNext(CharSequence item) { this.builder.append(item); }
	@Override public void onError(Throwable throwable) { ; }
	@Override public void onComplete() { this.result = this.mapper.apply(this.builder); }

	public T result() { return this.result; }
}
