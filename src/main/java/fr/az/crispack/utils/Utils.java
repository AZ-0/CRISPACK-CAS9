package fr.az.crispack.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import fr.az.crispack.App;

public class Utils
{
	public static boolean isDigit(int character) { return Utils.isDigit((char) character); }
	public static boolean isDigit(char c) { return c >= '0' && c <= '9'; }
	public static boolean isAlpha(char c) { return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'z'; }
	public static boolean isAlphaNumeric(char c) { return Utils.isAlpha(c) || Utils.isDigit(c); }

	//FUCK YOU JAVA GENERICITY AND RAW TYPES

	public static String getContent(File file)
	{
		return Utils.safeOp(file.toPath(), Files::readString, () -> "", () -> "");
	}

	public static String getContent(InputStream stream)
	{
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream)))
		{
			return reader.lines().reduce("", (a, b) -> a + b);
		} catch (IOException e)
		{
			App.logger().error(e);
			return "";
		}
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
		return Utils.safeOp(in, op, Utils.errorLogger(() -> null), () -> null); }

	public static <R, U, T extends Throwable> U safeOp(R in, CheckedFunction<R, U, T> op, Supplier<U> checked) {
		return Utils.safeOp(in, op, Utils.errorLogger(checked), () -> null); }

	public static <R, U, T extends Throwable> U safeOp(R in, CheckedFunction<R, U, T> op, Supplier<U> checked, Supplier<U> unchecked) {
		return Utils.safeOp(in, op, Utils.errorLogger(checked), unchecked); }

	public static <R, U, T extends Throwable> U safeOp(R in, CheckedFunction<R, U, T> op, Function<T, U> checked, Supplier<U> valueOnUnchecked) {
		return Utils.safeOp(in, op, checked, Utils.errorLogger(valueOnUnchecked)); }

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

	public static <T extends Throwable, U> Function<T, U> errorLogger(Supplier<U> value)
	{
		return error ->
		{
			App.logger().error(error);
			return value.get();
		};
	}
}
