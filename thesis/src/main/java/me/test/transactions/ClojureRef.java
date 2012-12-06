package me.test.transactions;

public final class ClojureRef<E> implements Ref<E> {
	
	public final clojure.lang.Ref ref;
	
	public ClojureRef(E initialValue) {
		ref = new clojure.lang.Ref(initialValue);
	}

	@SuppressWarnings("unchecked")
	public E updateValue(E newValue) {
		return (E) ref.set(newValue);
	}

	@SuppressWarnings("unchecked")
	public E deref() {
		return (E)ref.deref();
	}

}
