package me.test.test.concurrency.collectiontest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import me.test.transactions.BlockingRef;
import me.test.transactions.BlockingTransactionManager;
import clojure.lang.PersistentVector;

public class BlockingCollectionTest implements ConcurrentCollectionTest {

	private final BlockingRef<PersistentVector> listRef;
	
	public String getGroupName() {
		return "Blocking";
	}
	
	public BlockingCollectionTest()  {
		
		this.listRef = new BlockingRef<PersistentVector>(PersistentVector.create(Integer.valueOf(0)));
		
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
}
