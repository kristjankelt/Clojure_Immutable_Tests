package me.test.test.concurrency.collectiontest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class NativeOptimisticCollectionTest implements ConcurrentCollectionTest {

	private final AtomicReference<List<Integer>> listRef;
	
	public String getGroupName() {
		return "NativeAtomic";
	}
	
	public NativeOptimisticCollectionTest() {
		
		this.listRef = new AtomicReference<List<Integer>>(
				new ArrayList<Integer>(
					Arrays.asList(0)	
				)
		);
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

}
