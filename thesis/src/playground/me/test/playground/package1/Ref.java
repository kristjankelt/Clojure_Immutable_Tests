package me.test.playground.package1;

public class Ref<E> {
	
	private E value;
	
	public static class UpdateStrategy<T> {
		private final Ref<T> ref;
		
		public UpdateStrategy(Ref<T> ref) {
			this.ref = ref;
		}
		
		public final void updateValue(T value) {
			ref.updateRefInternalValue(value);
		}
	}
	
	public Ref(E value) {
		this.value = value;
	}
	
	public E getValue() {
		return value;
	}

	private void updateRefInternalValue(E value) {
		this.value = value;
	}
}
