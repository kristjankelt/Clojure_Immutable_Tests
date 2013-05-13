package me.test.test.concurrency.collectiontest;

import java.util.List;

import clojure.lang.PersistentVector;

public class SynchronizeCollectionTest3 implements ConcurrentCollectionTest {

	private PersistentVector list;
	
	@Override
	public void prepareTest(int initialSize) {
		this.list = PersistentVector.EMPTY;
		
		for (int i=0; i < initialSize; i++) {
			this.list = this.list.cons(Integer.valueOf(i));
		}
	}
	
	public String getGroupName() {
		return "Synchronized3";
	}
	
	public SynchronizeCollectionTest3()  {
		
		
	}
	
	public synchronized void addItem() {
		
		list = list.cons(Integer.valueOf(((Integer)list.peek()).intValue() + 1));
		

	}

	
	@SuppressWarnings("unchecked")
	public synchronized List<Integer> getResult() {
		return list;
	}

	@Override
	public long getSumOfFirstAndLast() {
		
		PersistentVector listTemp;
		
		synchronized(this) {
			listTemp = list;
		}
		
		if (listTemp.size() > 1) {
			return 
					((Integer)listTemp.nth(0)).longValue() +
					((Integer)listTemp.nth(listTemp.size()-1)).longValue();
		}
		else if  (listTemp.size() > 0) {
			return ((Integer)listTemp.nth(0)).longValue();
		}
		else {
			return 0;
		}
		 
	}

	@Override
	public long getSum() {
		PersistentVector listTemp;
		
		synchronized(this) {
			listTemp = list;
		}
		
		long sum = 0;
		
		for (Object item : listTemp) {
			sum += ((Integer)item).intValue();
		}
		
		return sum;
	}

	@Override
	public int getSize() {
		PersistentVector listTemp;
		
		synchronized(this) {
			listTemp = list;
		}
		
		return listTemp.size();
	}



	
}
