package me.test.test.concurrency.collectiontest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SynchronizeCollectionTest implements ConcurrentCollectionTest {

	private List<Integer> list;
	
	@Override
	public void prepareTest(int initialSize) {
		
		List<Integer> newList = new ArrayList<Integer>();
		
		for (int i=0; i < initialSize; i++) {
			newList.add(Integer.valueOf(i));
		}
		
		this.list = Collections.synchronizedList(newList);
	}
	
	public String getGroupName() {
		return "Synchronized";
	}
	
	public SynchronizeCollectionTest()  {
		
		
	}
	
	public void addItem() {
		
		synchronized (this) {
			list.add(list.get(list.size()-1).intValue() + 1);
		}
		
	}

	
	public List<Integer> getResult() {
		synchronized (this) {
			return new ArrayList<Integer>(list);
		}
	}

	@Override
	public long getSumOfFirstAndLast() {
		synchronized (list) {
			if (list.size() > 1) {
				return 
						(list.get(0)).longValue() +
						(list.get(list.size()-1)).longValue();
			}
			else if  (list.size() > 0) {
				return (list.get(0)).longValue();
			}
			else {
				return 0;
			}
		}
	}
	
	@Override
	public synchronized long getSum() {

		synchronized (list) {
			long sum = 0;
			
			for (Integer item : list) {
				sum += item.intValue();
			}
			
			return sum;
		}
		
	}

	@Override
	public int getSize() {
		synchronized (list) {
			return list.size();
		}
	}

	
}
