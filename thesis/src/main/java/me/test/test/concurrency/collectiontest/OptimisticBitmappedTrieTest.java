package me.test.test.concurrency.collectiontest;

import java.util.List;
import java.util.concurrent.Callable;

import me.test.transactions.OptimisticRef2;
import me.test.transactions.OptimisticTransactionManager2;
import me.test.util.BitmappedTrieVeryOld;

public class OptimisticBitmappedTrieTest implements ConcurrentCollectionTest {

	private OptimisticRef2<BitmappedTrieVeryOld<Integer>> listRef;
	
	@Override
	public void prepareTest(int initialSize) {
		
		BitmappedTrieVeryOld<Integer> list = new BitmappedTrieVeryOld<Integer>();

		for (int i=0; i < initialSize; i++) {
			list = list.addElement(Integer.valueOf(i));
		}
			
		this.listRef = new OptimisticRef2<BitmappedTrieVeryOld<Integer>>(
				list
			);
	}
	
	public String getGroupName() {
		return "OptimisticBitmappedTrie1";
	}
	
	public OptimisticBitmappedTrieTest() {
		
		
		
	}
	
	public void addItem() {
		
		OptimisticTransactionManager2.transaction(new Callable<Void>()  {

				public Void call() throws Exception {
				
					BitmappedTrieVeryOld<Integer> list = listRef.deref();
					
					list = list.addElement(Integer.valueOf(((Integer)list.lastAdded()).intValue() + 1));
					
					listRef.updateValue(list); 
					
					return null;
				}
		});
	}

	public List<Integer> getResult() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getSumOfFirstAndLast() {
		
		BitmappedTrieVeryOld<Integer> listTemp =
	
			OptimisticTransactionManager2.transaction(new Callable<BitmappedTrieVeryOld<Integer>>()  {
	
				public BitmappedTrieVeryOld<Integer> call() throws Exception {
					
					return listRef.deref();
				}
		});
			
		if (listTemp.size() > 1) {
			return 
					(listTemp.getElement(0)).longValue() +
					(listTemp.getElement(listTemp.size()-1)).longValue();
		}
		else if  (listTemp.size() > 0) {
			return (listTemp.getElement(0)).longValue();
		}
		else {
			return 0;
		}
	}
	
	@Override
	public long getSum() {
		BitmappedTrieVeryOld<Integer> list = 
				
				OptimisticTransactionManager2.transaction(new Callable<BitmappedTrieVeryOld<Integer>>()  {

					public BitmappedTrieVeryOld<Integer> call() throws Exception {
					
						return listRef.deref();
					}
		});
		
		long sum = 0;
		
		for (int i= 0; i < list.size(); i++) {
		
			sum += list.getElement(i);
		}
		
		return sum;
	}

	@Override
	public int getSize() {
		BitmappedTrieVeryOld<Integer> list = 
				
				OptimisticTransactionManager2.transaction(new Callable<BitmappedTrieVeryOld<Integer>>()  {

					public BitmappedTrieVeryOld<Integer> call() throws Exception {
					
						return listRef.deref();
					}
		});
		
		return list.size();
	
	}



}
