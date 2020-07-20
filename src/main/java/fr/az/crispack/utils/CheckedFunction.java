package fr.az.crispack.utils;

@FunctionalInterface
public interface CheckedFunction<I, O, T extends Throwable>
{
	public O apply(I in) throws T;
}
