package me.test.test.concurrency.collectiontest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import clojure.lang.PersistentVector;

import me.test.transactions.OptimisticRef2;
import me.test.util.BitmappedTrieOld2;

public class NativeOptimisticCollectionTest implements ConcurrentCollectionTest {

	private AtomicReference<List<Integer>> listRef;
	
	@Override
	public void prepareTest(int initialSize) {
		
		
		List<Integer> list = new ArrayList<Integer>();
			
		for (int i=0; i < initialSize; i++) {
			list.add(Integer.valueOf(i));
		}
		
		this.listRef = new AtomicReference<List<Integer>>(
				list
		);
	}
	
	public String getGroupName() {
		return "NativeAtomic";
	}
	
	public NativeOptimisticCollectionTest() {
		
		
	}
	
	@SuppressWarnings("serial")
	public void addItem() {
		
		List<Integer> list = listRef.get();
		final List<Integer> listCopy = list;
		
		while (!listRef.compareAndSet(
				list, 
				new ArrayList<Integer>(list) {{
					add(listCopy.get(listCopy.size()-1).intValue() + 1);
				}})) {
			
			list = listRef.get();
		}
			
	}

	public List<Integer> getResult() {
		return new ArrayList<Integer>(listRef.get());
	}

	@Override
	public long getSumOfFirstAndLast() {
		
		List<Integer> list = listRef.get();
		
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
		List<Integer> list = listRef.get();
		
		return list.size();
	}

}
