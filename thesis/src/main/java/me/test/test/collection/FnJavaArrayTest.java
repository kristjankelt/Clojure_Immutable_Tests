package me.test.test.collection;

import me.test.test.TooBigTestSizeException;

/*
 * THIS TEST IS NOT FINALIZED YET!!!
 * NOR IS IT ADDED INTO TEST LIST
 */
public final class FnJavaArrayTest<E> implements CollectionTest<E>  {

	public String groupName() {
		return "fj.data.Array";
	}
	
	private fj.data.Array<E> list;
	
	public void prepareTestEmpty() {
//		@SuppressWarnings("unchecked")
		fj.data.Array<E> newList = fj.data.Array.empty();
		
		list = newList;
	}
	
	public void prepareTest(java.util.List<E> data) {
		// TODO: find more type safe way for this 
			
		@SuppressWarnings("unchecked")
		fj.data.List<E> newList = fj.data.List.list();
		
		for (E element : data) {
			
			newList = newList.cons(element);
		}
		
		//list = newList;
		
	}
	
	public void normalFill(int testSize) {
		if (testSize > 100) {
			throw new TooBigTestSizeException();
		}
		
		fj.data.List<Integer> list = fj.data.List.list();
		 
		for (int i = 0; i < testSize; i++) {
		
			list = list.cons(Integer.valueOf(0));
		}
	}

	public void fastFill(int testSize) {
		normalFill(testSize);
	}

	public void addElement(E element) {
		//list = list.cons(element);
	}

	public E readElement(int index) {
		
		return null;
		//return list.index(index);
	}

	public void calculateSize() {
		@SuppressWarnings("unused")
		int size = list.length();
		//System.out.println(size);
	}

	public Iterable<E> iterable(int testSize) {
		return list;
	}

	public Iterable<E> reverseIterable(int testSize) {
		return list.reverse();
	}

	public void createCopy() {
//		@SuppressWarnings("unused")
//		fj.data.List<E> copy = list;
	}

	public void fillSafeLimit(int testSize) {
		
	}

	public void readSafeLimit(int testSize) {
		if (testSize > 1000) {
			throw new TooBigTestSizeException();
		}
	}

	public void changeElement(int index, E element) {
		throw new UnsupportedOperationException();
	}
}
