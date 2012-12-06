package me.test.test.collection;

import me.test.util.ImmutableArrayList2;

public final class ImmutableArrayListTest2<E> implements CollectionTest<E>  {

	public String groupName() {
		return "ImmutableArrayList2";
	}
	
	
	public void prepareTestEmpty() {

		
	}
	
	public void prepareTest(java.util.List<E> data) {
	
		throw new UnsupportedOperationException();
		
	}
	
	public void normalFill(int testSize) {
		ImmutableArrayList2<Integer> list = new ImmutableArrayList2<Integer>();
		
		for (int i=0; i < testSize; i++) {
			
			list = list.addElement(Integer.valueOf(0));
		}
	}

	public void fastFill(int testSize) {
		ImmutableArrayList2<Integer> list = new ImmutableArrayList2<Integer>();
		
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
