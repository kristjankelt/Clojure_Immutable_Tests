package me.test.test.concurrency.collectiontest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import me.test.transactions.OptimisticRef2;
import me.test.transactions.OptimisticTransactionManager2;
import clojure.lang.PersistentVector;

public class OptimisticCollectionTest2 implements ConcurrentCollectionTest {

	private final OptimisticRef2<PersistentVector> listRef;
	
	public String getGroupName() {
		return "Optimistic2";
	}
	
	public OptimisticCollectionTest2() {
		
		this.listRef = new OptimisticRef2<PersistentVector>(PersistentVector.create(Integer.valueOf(0)));
		
	}
	
	public void addItem() {
		
		OptimisticTransactionManager2.transaction(new Callable<Void>()  {

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
