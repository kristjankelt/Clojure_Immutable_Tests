package me.test.test.concurrency.collectiontest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clojure.lang.PersistentVector;

public class SynchronizeCollectionTest2 implements ConcurrentCollectionTest {

	private List<Integer> list;
	
	@Override
	public void prepareTest(int initialSize) {
		this.list = new ArrayList<Integer>();
		
		for (int i=0; i < initialSize; i++) {
			this.list.add(Integer.valueOf(i));
		}
	}
	
	public String getGroupName() {
		return "Synchronized2";
	}
	
	public SynchronizeCollectionTest2()  {
		
		
	}
	
	public synchronized void addItem() {
		
			list.add(list.get(list.size()-1).intValue() + 1);

	}

	
	public synchronized List<Integer> getResult() {
		return new ArrayList<Integer>(list);
	}

	@Override
	public synchronized long getSumOfFirstAndLast() {

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
	
	@Override
	public synchronized long getSum() {

		
		long sum = 0;
		
		for (Integer item : list) {
			sum += item.intValue();
		}
		
		return sum;
	}

	@Override
	public synchronized int getSize() {
		return list.size();
	}

	
}
