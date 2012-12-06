package me.test.test.concurrency.collectiontest;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import clojure.lang.PersistentVector;

public class AtomicCollectionTest implements ConcurrentCollectionTest {

	private final AtomicReference<PersistentVector> listRef;
	
	public String getGroupName() {
		return "Atomic";
	}
	
	public AtomicCollectionTest()  {
		
		this.listRef = new AtomicReference<PersistentVector>(
					PersistentVector.create(Integer.valueOf(0)));
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

	
}
