package fr.az.crispack.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import fr.az.crispack.App;
import fr.az.crispack.property.Header;

public class Util
{
	public static boolean isDigit(int character) { return Util.isDigit((char) character); }
	public static boolean isDigit(char c) { return c >= '0' && c <= '9'; }
	public static boolean isAlpha(char c) { return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'z'; }
	public static boolean isAlphaNumeric(char c) { return Util.isAlpha(c) || Util.isDigit(c); }

	//FUCK YOU JAVA GENERICITY AND RAW TYPES

	public static String getContent(File file)
	{
		return Util.safeOp(file.toPath(), Files::readString, () -> "", () -> "");
	}

	public static String getContent(InputStream stream)
	{
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream)))
		{
			return reader.lines().reduce("", String::concat);
		} catch (IOException e)
		{
			App.logger().error(e);
			return "";
		}
	}

	public static boolean exists	(Path path) { return path != null && Files.exists(path); }
	public static boolean existsDir	(Path path) { return exists(path) && Files.isDirectory(path); }
	public static boolean existsFile(Path path) { return exists(path) && !Files.isDirectory(path); }

	public static Stream<Path> list(Path path)
	{
		try
		{
			return Files.list(path);
		} catch (IOException e)
		{
			App.logger().error(e);
			return Stream.empty();
		}
	}

	public static Stream<Path> listDirs	(Path path) { return list(path).filter(Util::existsDir); }
	public static Stream<Path> listFiles(Path path) { return list(path).filter(Util::existsFile); }

	public static HttpRequest.Builder request()
	{
		HttpRequest.Builder builder = HttpRequest.newBuilder();

		for (Header header : Header.values())
			builder.setHeader(header.header(), header.value());

		return builder;
	}

	@SafeVarargs
	public static <T> Set<T> setOf(T... array)
	{
		Set<T> set = new HashSet<>();

		for (T v : array)
			set.add(v);

		return set;
	}

	public static <C extends Comparable<C>> C max(C first, C second)
	{
		if (first.compareTo(second) > 0)
			return first;

		return second;
	}

	public static <C extends Comparable<C>> C min(C first, C second)
	{
		if (first.compareTo(second) < 0)
			return first;

		return second;
	}

	// OPERATIONS

	public static <R, U, T extends Throwable> U safeOp(R in, CheckedFunction<R, U, T> op) {
		return safeOp(in, op, runtimeThrower(), runtimeThrower()); }

	public static <R, U, T extends Throwable> U safeOp(R in, CheckedFunction<R, U, T> op, Supplier<U> checked) {
		return safeOp(in, op, errorLogger(checked), runtimeThrower()); }

	public static <R, U, T extends Throwable> U safeOp(R in, CheckedFunction<R, U, T> op, Supplier<U> checked, Supplier<U> unchecked) {
		return safeOp(in, op, errorLogger(checked), unchecked); }

	public static <R, U, T extends Throwable> U safeOp(R in, CheckedFunction<R, U, T> op, Function<T, U> checked, Supplier<U> unchecked) {
		return safeOp(in, op, checked, errorLogger(unchecked)); }

	public static <R, U, T extends Throwable> U safeOp(R in, CheckedFunction<R, U, T> op, Function<T, U> checked, Function<Throwable, U> unchecked)
	{
		try
		{
			return op.apply(in);
		} catch (Throwable throwable)
		{
			if (unchecked instanceof T error)
				return checked.apply(error);

			return unchecked.apply(throwable);
		}
	}

	public static <T extends Throwable, U> Function<T, U> runtimeThrower()
	{
		return Util.<T, U, RuntimeException>errorThrower(RuntimeException::new)::apply;
	}

	public static <T extends Throwable, U, R extends Throwable> CheckedFunction<T, U, R> errorThrower(Function<T, R> errorMapper)
	{
		return error -> { throw errorMapper.apply(error); };
	}

	public static <T extends Throwable, U> Function<T, U> errorLogger(Supplier<U> value)
	{
		return error ->
		{
			App.logger().error(error);
			return value.get();
		};
	}
}
