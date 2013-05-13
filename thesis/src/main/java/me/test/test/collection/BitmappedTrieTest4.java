package me.test.test.collection;

import me.test.util.BitmappedTrieNew;
import me.test.util.FastArrayList;

public final class BitmappedTrieTest4<E> implements CollectionTest<E>  {
	
	private BitmappedTrieNew<E> testList;

	public String groupName() {
		return "BitmappedTrie4";
	}
	
	
	public void prepareTestEmpty() {

		testList = new BitmappedTrieNew<E>();
		
	}
	
	public void prepareTest(java.util.List<E> data) {
		
		testList = new BitmappedTrieNew<E>(data);
		
	}
	
	public void normalFill(int testSize) {
		BitmappedTrieNew<Integer> newList = new BitmappedTrieNew<Integer>();
		
		for (int i=0; i < testSize; i++) {
			
			newList = newList.addElement(Integer.valueOf(0));
		}
		
	}

	public void fastFill(int testSize) {
		FastArrayList<Integer> list = new FastArrayList<Integer>();
		
		for (int i=0; i < testSize; i++) {
			
			list.addElement(Integer.valueOf(0));
		}
		
		@SuppressWarnings("unused")
		BitmappedTrieNew<Integer> newList = new BitmappedTrieNew<Integer>(list);
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
