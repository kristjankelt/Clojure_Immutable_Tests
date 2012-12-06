package me.test.test.concurrency.collectiontest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import me.test.transactions.OptimisticRef2;
import me.test.transactions.OptimisticTransactionManager2;
import me.test.util.BitmappedTrie;

public class OptimisticBitmappedTrieTest implements ConcurrentCollectionTest {

	private final OptimisticRef2<BitmappedTrie<Integer>> listRef;
	
	public String getGroupName() {
		return "OptimisticBitmappedTrie";
	}
	
	public OptimisticBitmappedTrieTest() {
		
		this.listRef = new OptimisticRef2<BitmappedTrie<Integer>>(
					new BitmappedTrie<Integer>(Arrays.asList(0))
				);
		
	}
	
	public void addItem() {
		
		OptimisticTransactionManager2.transaction(new Callable<Void>()  {

				public Void call() throws Exception {
				
					BitmappedTrie<Integer> list = listRef.deref();
					
					list = list.addElement(Integer.valueOf(((Integer)list.lastAdded()).intValue() + 1));
					
					listRef.updateValue(list); 
					
					return null;
				}
		});
	}

	public List<Integer> getResult() {
		throw new UnsupportedOperationException();
	}

}
