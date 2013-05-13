package me.test.test.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class ArrayListTest<E> implements CollectionTest<E>  {

	public String groupName() {
		return "java.util.ArrayList";
	}
	
	private List<E> list;
	
	public void prepareTestEmpty() {

		List<E> newList = new ArrayList<E>();
		
		list = newList;
	}
	
	public void prepareTest(java.util.List<E> data) {
	
		List<E> newList = new ArrayList<E>();
		
		for (E element : data) {
			
			newList.add(element);
		}
		
		list = newList;
		
	}
	
	public void normalFill(int testSize) {
		List<Integer> newList = new ArrayList<Integer>();
		
		for (int i=0; i < testSize; i++) {
			
			newList.add(Integer.valueOf(0));
		}
		
	}

	public void fastFill(int testSize) {
		List<Integer> newList = new ArrayList<Integer>();
		
		for (int i=0; i < testSize; i++) {
			
			newList.add(Integer.valueOf(0));
		}
	}

	public void addElement(E element) {
		list.add(element);
	}

	public E readElement(int index) {
		
		return list.get(index);
	}
	
	public void changeElement(int index, E element) {
		
		list.set(index, element);
	}
	

	public void calculateSize() {
		@SuppressWarnings("unused")
		int size = list.size();
	}

	public Iterable<E> iterable(int testSize) {
		return list;
	}

	public Iterable<E> reverseIterable(int testSize) {
		List<E> reversedList = new ArrayList<E>(list);
		
		Collections.reverse(reversedList);
		
		return reversedList;
	}

	public void createCopy() {
		@SuppressWarnings("unused")
		List<E> copy = new ArrayList<E>(list);
	}

	public void fillSafeLimit(int testSize) {
		
	}

	public void readSafeLimit(int testSize) {
		
	}

	
}
