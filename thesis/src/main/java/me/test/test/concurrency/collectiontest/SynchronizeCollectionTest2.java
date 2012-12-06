package me.test.test.concurrency.collectiontest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SynchronizeCollectionTest2 implements ConcurrentCollectionTest {

	private final List<Integer> list;
	
	public String getGroupName() {
		return "Synchronized2";
	}
	
	public SynchronizeCollectionTest2()  {
		
		this.list = new ArrayList<Integer>(Arrays.asList(0));
	}
	
	public synchronized void addItem() {
		
			list.add(list.get(list.size()-1).intValue() + 1);

	}

	
	public synchronized List<Integer> getResult() {
		return new ArrayList<Integer>(list);
	}

	
}
