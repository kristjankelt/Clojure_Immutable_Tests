package me.test.test.collection;

import me.test.util.ImmutableArrayList;

public final class ImmutableArrayListTest<E> implements CollectionTest<E>  {

	public String groupName() {
		return "ImmutableArrayList";
	}
	
	
	public void prepareTestEmpty() {

		
	}
	
	public void prepareTest(java.util.List<E> data) {
	
		throw new UnsupportedOperationException();
		
	}
	
	public void normalFill(int testSize) {
		ImmutableArrayList<Integer> list = new ImmutableArrayList<Integer>();
		
		for (int i=0; i < testSize; i++) {
			
			list = list.addElement(Integer.valueOf(0));
		}
	}

	public void fastFill(int testSize) {
		ImmutableArrayList<Integer> list = new ImmutableArrayList<Integer>();
		
		for (int i=0; i < testSize; i++) {
			
			list = list.addElement(Integer.valueOf(0));
		}

	}

	public void addElement(E element) {
		throw new UnsupportedOperationException();
	}

	public E readElement(int index) {
		
		throw new UnsupportedOperationException();
	}

	public void calculateSize() {
		throw new UnsupportedOperationException();
	}

	public Iterable<E> iterable(int testSize) {
		throw new UnsupportedOperationException();
	}

	public Iterable<E> reverseIterable(int testSize) {
		throw new UnsupportedOperationException();
	}

	public void createCopy() {
		throw new UnsupportedOperationException();
	}

	public void fillSafeLimit(int testSize) {
		
	}

	public void readSafeLimit(int testSize) {
		
	}
	
}
