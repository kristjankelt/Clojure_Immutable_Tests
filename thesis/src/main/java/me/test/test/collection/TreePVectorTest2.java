package me.test.test.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.pcollections.ConsPStack;
import org.pcollections.PStack;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

public final class TreePVectorTest2<E> implements CollectionTest<E>  {

	public String groupName() {
		return "org.pcollections.TreePVector";
	}
	
	private PVector<E> list;
	
	public void prepareTestEmpty() {

		PVector<E> newList = TreePVector.empty();
		
		list = newList;
	}
	
	public void prepareTest(java.util.List<E> data) {
	
		PVector<E> newList = TreePVector.empty();
		
		for (E element : data) {
			newList = newList.plus(element);
		}
		
		list = newList;
		
	}
	
	public void normalFill(int testSize) {
		PVector<Integer> newList = TreePVector.empty();
		
		for (int i=0; i < testSize; i++) {
			newList = newList.plus(Integer.valueOf(0));
		}
		
	}

	public void fastFill(int testSize) {
		List<Integer> newList = new ArrayList<Integer>(testSize);
		
		
		for (int i=0; i < testSize; i++) {
			newList.add(Integer.valueOf(0));
		}
		
		@SuppressWarnings("unused")
		PVector<Integer> immutableList = TreePVector.from(newList);
		
	}

	public void addElement(E element) {
		list = list.plus(element);
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
		return reverse(list);
	}
	
	private static <T> PStack<T> reverse(final Collection<? extends T> list) {
        PStack<T> rev = ConsPStack.empty();
        for(T e : list)
                rev = rev.plus(e);
        return rev;
	}

	public void createCopy() {
		@SuppressWarnings("unused")
		PVector<E> copy = list;
	}

	public void fillSafeLimit(int testSize) {
		
	}

	public void readSafeLimit(int testSize) {
		
	}
}
