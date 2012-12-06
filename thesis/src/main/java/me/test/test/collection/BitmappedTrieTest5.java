package me.test.test.collection;

import me.test.util.BitmappedTrieOld2;
import me.test.util.FastArrayList;

public final class BitmappedTrieTest5<E> implements CollectionTest<E>  {

	public String groupName() {
		return "BitmappedTrie5";
	}
	
	
	public void prepareTestEmpty() {

		
	}
	
	public void prepareTest(java.util.List<E> data) {
	
		throw new UnsupportedOperationException();
		
	}
	
	public void normalFill(int testSize) {
		BitmappedTrieOld2<Integer> newList = new BitmappedTrieOld2<Integer>();
		
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
		BitmappedTrieOld2<Integer> newList = new BitmappedTrieOld2<Integer>(list);
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
