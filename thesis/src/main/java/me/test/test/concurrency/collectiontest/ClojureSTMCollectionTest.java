package me.test.test.concurrency.collectiontest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import me.test.transactions.ClojureRef;
import clojure.lang.LockingTransaction;
import clojure.lang.PersistentVector;

public class ClojureSTMCollectionTest implements ConcurrentCollectionTest {

	private final ClojureRef<PersistentVector> listRef;
	
	public String getGroupName() {
		return "ClojureSTM";
	}
	
	public ClojureSTMCollectionTest() {
		
		this.listRef = new ClojureRef<PersistentVector>(PersistentVector.create(Integer.valueOf(0)));
		
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

}
