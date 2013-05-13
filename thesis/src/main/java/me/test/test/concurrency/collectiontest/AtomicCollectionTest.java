package me.test.test.concurrency.collectiontest;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import clojure.lang.PersistentVector;

public class AtomicCollectionTest implements ConcurrentCollectionTest {

	private AtomicReference<PersistentVector> listRef;
	
	@Override
	public void prepareTest(int initialSize) {
		
		PersistentVector list = PersistentVector.EMPTY;
		
		for (int i=0; i < initialSize; i++) {
			list = list.cons(Integer.valueOf(i));
		}
		
		this.listRef = new AtomicReference<PersistentVector>(
				list
		);
		
	}
	
	public String getGroupName() {
		return "Atomic";
	}
	
	public AtomicCollectionTest()  {
		
		
	}
	
	public void addItem() {
		
		
		PersistentVector list = listRef.get();
		
		while (!listRef.compareAndSet(
					list, 
					list.cons(
							Integer.valueOf(((Integer)list.peek()).intValue() + 1)))) {
			
			list = listRef.get();
			
		}
		
		
	}

	
	@SuppressWarnings("unchecked")
	public List<Integer> getResult() {
		return listRef.get();
	}

	@Override
	public long getSumOfFirstAndLast() {
		PersistentVector list = listRef.get();
		
		if (list.size() > 1) {
			return 
					((Integer)list.get(0)).longValue() +
					((Integer)list.get(list.size()-1)).longValue();
		}
		else if  (list.size() > 0) {
			return ((Integer)list.get(0)).longValue();
		}
		else {
			return 0;
		}
	}

	@Override
	public long getSum() {
		PersistentVector list = listRef.get();
		
		long sum = 0;
		
		for (Object item : list) {
			sum += ((Integer)item).intValue();
		}
		
		return sum;
	}

	@Override
	public int getSize() {
		PersistentVector list = listRef.get();
		
		return list.size();
	}

	

	
}
