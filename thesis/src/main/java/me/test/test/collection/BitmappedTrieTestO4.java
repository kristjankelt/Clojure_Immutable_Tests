package me.test.test.collection;

import me.test.util.BitmappedTrieBasic2;

public final class BitmappedTrieTestO4<E> implements CollectionTest<E>  {
	
	private BitmappedTrieBasic2<E> testList;

	public String groupName() {
		return "BitmappedTrie4";
	}
	
	
	public void prepareTestEmpty() {

		testList = new BitmappedTrieBasic2<E>();
		
	}
	
	public void prepareTest(java.util.List<E> data) {
	
		testList = new BitmappedTrieBasic2<E>();
		
		for (E item : data) {
			testList.addElement(item);
		}
		
	}
	
	public void normalFill(int testSize) {
		BitmappedTrieBasic2<Integer> newList = new BitmappedTrieBasic2<Integer>();
		
		for (int i=0; i < testSize; i++) {
			
			newList = newList.addElement(Integer.valueOf(0));
		}
		
	}

	public void fastFill(int testSize) {
		throw new UnsupportedOperationException();
	}

	public void addElement(E element) {
		throw new UnsupportedOperationException();
	}

	public E readElement(int index) {
		
		return testList.getElement(index);
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

	public void changeElement(int index, E element) {
		throw new UnsupportedOperationException();
	}
}
