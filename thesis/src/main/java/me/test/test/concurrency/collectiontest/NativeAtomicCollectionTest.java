package me.test.test.concurrency.collectiontest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import me.test.transactions.OptimisticRef;
import me.test.transactions.OptimisticTransactionManager;

public class NativeAtomicCollectionTest implements ConcurrentCollectionTest {

	private final OptimisticRef<List<Integer>> listRef;
	
	public String getGroupName() {
		return "NativeOptimistic";
	}
	
	public NativeAtomicCollectionTest() {
		
		this.listRef = new OptimisticRef<List<Integer>>(
				new ArrayList<Integer>(
					Arrays.asList(0)	
				)
		);
	}
	
	public void addItem() {
		
		OptimisticTransactionManager.transaction(new Callable<Void>()  {

				public Void call() throws Exception {
					
					List<Integer> list = new ArrayList<Integer>(listRef.deref());
					
					list.add(list.get(list.size()-1).intValue() + 1);
					
					listRef.updateValue(list); 
					
					return null;
				}
		});
	}

	public List<Integer> getResult() {
		return new ArrayList<Integer>(listRef.deref());
	}

}
