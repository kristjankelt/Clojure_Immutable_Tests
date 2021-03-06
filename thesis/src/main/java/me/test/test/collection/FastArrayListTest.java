package me.test.test.collection;

import me.test.util.FastArrayList;

public final class FastArrayListTest<E> implements CollectionTest<E>  {

	private FastArrayList<E> testList;
	
	public String groupName() {
		return "FastArrayList";
	}
	
	
	public void prepareTestEmpty() {

		testList = new FastArrayList<E>();
	
	}
	
	public void prepareTest(java.util.List<E> data) {
	
		testList = new FastArrayList<E>();
		
		for (E item : data) {
			testList.addElement(item);
		}
	}
	
	public void normalFill(int testSize) {
		FastArrayList<Integer> list = new FastArrayList<Integer>();
		
		for (int i=0; i < testSize; i++) {
			
			list.addElement(Integer.valueOf(0));
		}
	}

	public void fastFill(int testSize) {
		FastArrayList<Integer> list = new FastArrayList<Integer>();
		
		for (int i=0; i < testSize; i++) {
			
			list.addElement(Integer.valueOf(0));
		}
	
	}

	public void addElement(E element) {
		testList.addElement(element);
	}

	public E readElement(int index) {
		
		return testList.getElement(index);
	}

	public void calculateSize() {
		@SuppressWarnings("unused")
		int i = testList.size();
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

	public void changeElement(int index, E element) {
		testList.setElement(index, element);
	}
}
