package me.test.test.concurrency.collectiontest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import me.test.transactions.OptimisticRef2;
import me.test.transactions.OptimisticTransactionManager;
import me.test.transactions.OptimisticTransactionManager2;
import me.test.util.debug.StateDebuger;
import clojure.lang.PersistentVector;

public class OptimisticCollectionTest2 implements ConcurrentCollectionTest {

	private OptimisticRef2<PersistentVector> listRef;
	
	@Override
	public void prepareTest(int initialSize) {
		
		PersistentVector list = PersistentVector.EMPTY;

		for (int i=0; i < initialSize; i++) {
			list = list.cons(Integer.valueOf(i));
		}
		
		this.listRef = new OptimisticRef2<PersistentVector>(list);
		
	}
	
	public String getGroupName() {
		return "Optimistic2";
	}
	
	public OptimisticCollectionTest2() {
		
		
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

	@Override
	public long getSumOfFirstAndLast() {
		PersistentVector list = 
		
				OptimisticTransactionManager2.transaction(new Callable<PersistentVector>()  {

					public PersistentVector call() throws Exception {
					
						return listRef.deref();
					}
		});
		
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
		PersistentVector list = 
				
				OptimisticTransactionManager2.transaction(new Callable<PersistentVector>()  {

					public PersistentVector call() throws Exception {
					
						return listRef.deref();
					}
		});
		
		long sum = 0;
		
		for (Object item : list) {
			sum += ((Integer)item).intValue();
		}
		
		return sum;
	}

	@Override
	public int getSize() {
		PersistentVector list = 
				
				OptimisticTransactionManager2.transaction(new Callable<PersistentVector>()  {

					public PersistentVector call() throws Exception {
					
						return listRef.deref();
					}
		});
		
		return list.size();
	}

}
