package me.test.transactions;

public interface Passable<E> {

	public E call(E value);
}
