package me.test.test.concurrency.collectiontest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import me.test.transactions.ClojureRef;
import me.test.transactions.OptimisticRef;
import me.test.transactions.OptimisticTransactionManager;

public class NativeAtomicCollectionTest implements ConcurrentCollectionTest {

	private OptimisticRef<List<Integer>> listRef;
	
	@Override
	public void prepareTest(int initialSize) {
		
		List<Integer> list = new ArrayList<Integer>();
		
		for (int i=0; i < initialSize; i++) {
			list.add(Integer.valueOf(i));
		}
		
		this.listRef = new OptimisticRef<List<Integer>>(
				list
		);
	}
	
	public String getGroupName() {
		return "NativeOptimistic";
	}
	
	public NativeAtomicCollectionTest() {
		
		
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

	@Override
	public long getSumOfFirstAndLast() {
		List<Integer> list = listRef.deref();
		
		if (list.size() > 1) {
			return 
					(list.get(0)).longValue() +
					(list.get(list.size()-1)).longValue();
		}
		else if  (list.size() > 0) {
			return (list.get(0)).longValue();
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
		List<Integer> list = listRef.deref();
		
		return list.size();
	}

}
