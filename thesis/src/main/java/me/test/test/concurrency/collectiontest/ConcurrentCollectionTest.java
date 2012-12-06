package me.test.test.concurrency.collectiontest;

import java.util.List;

public interface ConcurrentCollectionTest {
	
	public String getGroupName(); 

	public void addItem();
	
	public List<Integer> getResult();
	
}
