package me.test.test.concurrency.collectiontest;

import java.util.List;

public interface ConcurrentCollectionTest {
	
	public void prepareTest(int initialSize);
	
	public String getGroupName(); 

	public void addItem();
	
	public List<Integer> getResult();
	
	public long getSumOfFirstAndLast();
	
	public long getSum();
	
	public int getSize();
	
}
