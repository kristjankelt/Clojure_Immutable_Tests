package me.test.test.concurrency.collectiontest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import me.test.transactions.BlockingRef;
import me.test.transactions.BlockingTransactionManager;
import me.test.transactions.ClojureRef;
import clojure.lang.PersistentVector;

public class BlockingCollectionTest implements ConcurrentCollectionTest {

	private BlockingRef<PersistentVector> listRef;
	
	@Override
	public void prepareTest(int initialSize) {
		
		PersistentVector list = PersistentVector.EMPTY;
		
		for (int i=0; i < initialSize; i++) {
			list = list.cons(Integer.valueOf(i));
		}
		
		this.listRef = new BlockingRef<PersistentVector>(
				list
		);
	}
	
	public String getGroupName() {
		return "Blocking";
	}
	
	public BlockingCollectionTest()  {
		
		
	}
	
	public void addItem() {
		
		BlockingTransactionManager.transaction(new Callable<Void>()  {

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
		throw new UnsupportedOperationException();
	}

	@Override
	public int getSize() {
		PersistentVector list = listRef.deref();
		
		return list.size();
	}
}
