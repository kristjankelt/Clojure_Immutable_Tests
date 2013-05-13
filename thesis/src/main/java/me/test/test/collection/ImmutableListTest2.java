package me.test.test.collection;

import java.util.ArrayList;
import java.util.List;

import me.test.test.TooBigTestSizeException;

import com.google.common.collect.ImmutableList;


public final class ImmutableListTest2<E> implements CollectionTest<E>  {

	public String groupName() {
		return "com.google.common.collect.ImmutableList";
	}
	
	private ImmutableList<E> list;
	
	public void prepareTestEmpty() {

		ImmutableList<E> newList = ImmutableList.of();
		
		list = newList;
	}
	
	public void prepareTest(java.util.List<E> data) {
	
		List<E> newList = new ArrayList<E>(data.size());
		
		
		for (E element : data) {
			newList.add(element);
		}
		
		list = ImmutableList.copyOf(newList);
		
	}
	
	public void normalFill(int testSize) {
		if (testSize > 1000) {
			throw new TooBigTestSizeException();
		}
		
		ImmutableList<Integer> newList = ImmutableList.of();
		
		for (int i=0; i < testSize; i++) {
			
			newList = ImmutableList.<Integer>builder().addAll(newList).add(Integer.valueOf(0)).build();

		}
		
	}

	public void fastFill(int testSize) {
		List<Integer> newList = new ArrayList<Integer>(testSize);
		
		
		for (int i=0; i < testSize; i++) {
			newList.add(Integer.valueOf(0));
		}
		
		@SuppressWarnings("unused")
		List<Integer> immutableList = ImmutableList.copyOf(newList);
	}

	public void addElement(E element) {
		list = ImmutableList.<E>builder().addAll(list).add(element).build();

	}

	public E readElement(int index) {
		
		return list.get(index);
	}

	public void calculateSize() {
		@SuppressWarnings("unused")
		int size = list.size();
		//System.out.println(size);
	}

	public Iterable<E> iterable(int testSize) {
		return list;
	}

	public Iterable<E> reverseIterable(int testSize) {
		return list.reverse();
	}

	public void createCopy() {
		@SuppressWarnings("unused")
		ImmutableList<E> copy = list;
	}
	
	public void fillSafeLimit(int testSize) {
		if (testSize > 100) {
			throw new TooBigTestSizeException();
		}
	}

	public void readSafeLimit(int testSize) {
		
	}

	public void changeElement(int index, E element) {
		throw new UnsupportedOperationException();
	}
}
