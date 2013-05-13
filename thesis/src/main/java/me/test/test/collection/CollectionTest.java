package me.test.test.collection;

import java.util.List;

public interface CollectionTest<E> {
	
	public String groupName();
	
	public void fillSafeLimit(int testSize);
	
	public void readSafeLimit(int testSize);
	
	public void prepareTestEmpty();
	
	public void prepareTest(List<E> data);
	
	public void normalFill(int testSize);
	
	public void fastFill(int testSize);

	public void addElement(E element);
	
	public void changeElement(int index, E element);
	
	public E readElement(int index);
	
	public void calculateSize();
	
	public void createCopy();
	
	public Iterable<E> iterable(int testSize);
	
	public Iterable<E> reverseIterable(int testSize);
	
}
