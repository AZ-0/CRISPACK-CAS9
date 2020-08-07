package fr.az.cytokine.util;

@FunctionalInterface
public interface CheckedFunction<I, O, T extends Throwable>
{
	public O apply(I in) throws T;
}
