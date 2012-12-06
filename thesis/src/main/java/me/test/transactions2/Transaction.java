package me.test.transactions2;

import java.util.concurrent.Callable;

public final class Transaction {

	public static <V> V transaction(Callable<V> transaction) {
		return null;
	}
	
	public static <E> E updateValue(final Ref<E> ref, final E newValue) {
		return null;
	}
	
	public static <E> E deref(final Ref<E> ref) {
		return null;
	}
}
