package me.test.test.concurrency.collectiontest;

import java.util.List;

import clojure.lang.PersistentVector;

public class SynchronizeCollectionTest3 implements ConcurrentCollectionTest {

	private PersistentVector list;
	
	public String getGroupName() {
		return "Synchronized3";
	}
	
	public SynchronizeCollectionTest3()  {
		
		this.list = PersistentVector.create(Integer.valueOf(0));
	}
	
	public synchronized void addItem() {
		
		list = list.cons(Integer.valueOf(((Integer)list.peek()).intValue() + 1));
		

	}

	
	@SuppressWarnings("unchecked")
	public synchronized List<Integer> getResult() {
		return list;
	}

	
}
