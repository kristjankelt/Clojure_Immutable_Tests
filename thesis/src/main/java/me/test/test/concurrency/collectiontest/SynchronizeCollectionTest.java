package me.test.test.concurrency.collectiontest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SynchronizeCollectionTest implements ConcurrentCollectionTest {

	private final List<Integer> list;
	
	public String getGroupName() {
		return "Synchronized";
	}
	
	public SynchronizeCollectionTest()  {
		
		this.list = Collections.synchronizedList(new ArrayList<Integer>(
				new ArrayList<Integer>(
						Arrays.asList(0)	
				)
		));
	}
	
	public void addItem() {
		
		synchronized (list) {
			list.add(list.get(list.size()-1).intValue() + 1);
		}
		
	}

	
	public List<Integer> getResult() {
		return new ArrayList<Integer>(list);
	}

	
}
