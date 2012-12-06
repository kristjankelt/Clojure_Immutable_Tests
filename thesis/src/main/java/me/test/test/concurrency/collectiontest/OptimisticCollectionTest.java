package me.test.test.concurrency.collectiontest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import me.test.transactions.OptimisticRef;
import me.test.transactions.OptimisticTransactionManager;
import clojure.lang.PersistentVector;

public class OptimisticCollectionTest implements ConcurrentCollectionTest {

	private final OptimisticRef<PersistentVector> listRef;
	
	public String getGroupName() {
		return "Optimistic";
	}
	
	public OptimisticCollectionTest() {
		
		this.listRef = new OptimisticRef<PersistentVector>(PersistentVector.create(Integer.valueOf(0)));
		
	}
	
	public void addItem() {
		
		OptimisticTransactionManager.transaction(new Callable<Void>()  {

				public Void call() throws Exception {
				
					PersistentVector list = listRef.deref();
					
					list = list.cons(Integer.valueOf(((Integer)list.peek()).intValue() + 1));
					
					listRef.updateValue(list); 
					
					return null;
				}
		});
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getResult() {
		return new ArrayList<Integer>(listRef.deref());
	}

}
