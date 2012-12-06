package me.test.transactions;

public interface Ref<E> {
	
	public E updateValue(final E newValue);
	public E deref();

}
