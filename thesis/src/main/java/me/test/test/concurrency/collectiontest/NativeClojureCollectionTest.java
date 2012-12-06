package me.test.test.concurrency.collectiontest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import me.test.transactions.ClojureRef;
import clojure.lang.LockingTransaction;

public class NativeClojureCollectionTest implements ConcurrentCollectionTest {

	private final ClojureRef<List<Integer>> listRef;
	
	public String getGroupName() {
		return "NativeClojure";
	}
	
	public NativeClojureCollectionTest() {
		
		this.listRef = new ClojureRef<List<Integer>>(
				new ArrayList<Integer>(
					Arrays.asList(0)	
				)
		);
		
	}
	
	public void addItem() {
		
		try {
			LockingTransaction.runInTransaction(new Callable<Void>()  {

				public Void call() throws Exception {
				
					List<Integer> list = new ArrayList<Integer>(listRef.deref());
					
					list.add(list.get(list.size()-1).intValue() + 1);
					
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

	public List<Integer> getResult() {
		return new ArrayList<Integer>(listRef.deref());
	}

}
