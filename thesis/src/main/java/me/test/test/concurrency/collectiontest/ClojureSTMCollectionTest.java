package me.test.test.concurrency.collectiontest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import me.test.transactions.ClojureRef;
import me.test.transactions.OptimisticRef;
import me.test.transactions.OptimisticTransactionManager;
import clojure.lang.LockingTransaction;
import clojure.lang.PersistentVector;

public class ClojureSTMCollectionTest implements ConcurrentCollectionTest {

	private ClojureRef<PersistentVector> listRef;
	
	@Override
	public void prepareTest(int initialSize) {
		
		PersistentVector list = PersistentVector.EMPTY;
		
		for (int i=0; i < initialSize; i++) {
			list = list.cons(Integer.valueOf(i));
		}
		
		this.listRef = new ClojureRef<PersistentVector>(
				list
		);
	}
	
	public String getGroupName() {
		return "ClojureSTM";
	}
	
	public ClojureSTMCollectionTest() {
		
		
	}
	
	public void addItem() {
		
		try {
			LockingTransaction.runInTransaction(new Callable<Void>()  {

				public Void call() throws Exception {
				
					PersistentVector list = listRef.deref();
					
					list = list.cons(Integer.valueOf(((Integer)list.peek()).intValue() + 1));
					
					listRef.updateValue(list); 
					
					return null;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			
		}
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getResult() {
		return new ArrayList<Integer>(listRef.deref());
	}

	@Override
	public long getSumOfFirstAndLast() {
		PersistentVector list = listRef.deref();
		
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
		PersistentVector list = null;
		
		try {
			list = (PersistentVector) LockingTransaction.runInTransaction(new Callable<PersistentVector>()  {

				public PersistentVector call() throws Exception {
				
					return listRef.deref();
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			
		}
		
		long sum = 0;
		
		for (Object item : list) {
			sum += ((Integer)item).intValue();
		}
		
		return sum;
	}

	@Override
	public int getSize() {
		PersistentVector list = listRef.deref();
		
		return list.size();
	}

}
