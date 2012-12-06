package me.test.test.collection;

import clojure.lang.ITransientVector;
import clojure.lang.PersistentVector;

public final class PersistentVectorTest2<E> implements CollectionTest<E>  {

	public String groupName() {
		return "clojure.lang.PersistentVector";
	}
	
	private PersistentVector list;
	
	public void prepareTestEmpty() {

		PersistentVector newList = PersistentVector.create();
		
		list = newList;
	}
	
	public void prepareTest(java.util.List<E> data) {
	
		PersistentVector newList = PersistentVector.create();
		
		for (E element : data) {
			newList = newList.cons(element);
					
		}
		
		list = newList;
		
	}
	
	public void normalFill(int testSize) {
		PersistentVector newList = PersistentVector.create();
		
		for (int i=0; i < testSize; i++) {
			newList = newList.cons(Integer.valueOf(0));
		}
		
	}

	public void fastFill(int testSize) {
		ITransientVector list = PersistentVector.EMPTY.asTransient();
		

		for (int i = 0; i < testSize; i++) {
			list = (ITransientVector) list.conj(Integer.valueOf(0));
		}
		
//		@SuppressWarnings("unused")
//		IPersistentCollection persistentList = list.persistent();

	}

	public void addElement(E element) {
		list = list.cons(element);
	}

	@SuppressWarnings("unchecked")
	public E readElement(int index) {
		return (E)list.nth(index);
	}

	public void calculateSize() {
		@SuppressWarnings("unused")
		int size = list.size();
		//System.out.println(size);
	}

	@SuppressWarnings("unchecked")
	public Iterable<E> iterable(int testSize) {
		return (Iterable<E>)list;
	}

	@SuppressWarnings("unchecked")
	public Iterable<E> reverseIterable(int testSize) {
		return (Iterable<E>)list.rseq();
	}
	
	public void createCopy() {
		@SuppressWarnings("unused")
		PersistentVector copy = list;
	}

	public void fillSafeLimit(int testSize) {
		
	}

	public void readSafeLimit(int testSize) {
		
	}
	
}
