package me.test.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import me.test.transactions.Passable;


public class BitmappedTrie<E> {

	private static final int TEST_SIZE = 320;
	
	final static int POWER = 5;
	
	final static int TAIL_SIZE = (int) Math.pow(2, POWER);
	
	private final static int LOCATOR = (int) Math.pow(2, POWER)-1;


	private Object[] data;
	private Object[] tail;
	private int tailIndex;
	private int index;
	private int level;
	private boolean canShareData;
	private AtomicBoolean stale;
	
	public BitmappedTrie() {
		this.data = new Object[0];
		this.tail = new Object[TAIL_SIZE];
		tailIndex = -1;
		index = -1;
		level = -1;
		canShareData = true;
		stale = new AtomicBoolean(false);
	}
	
	private void addTail(Object[] newTail) {
		
		if (tailIndex != -1) {
			throw new IllegalStateException();
		}
		
		index += newTail.length;				// TODO: WATCH THIS!!!
		
		if (data.length == 0) {					// TODO: change this 
			
			data = new Object[TAIL_SIZE];   	// TODO: change this 
			data[0] = newTail;
			level++;
		}
		else {
			connectTail(data, newTail, true);
		}

	}
	
	public BitmappedTrie(FastArrayList<E> list) {
		this();
		
		level = 0;
		
		int partitionCount = list.getDataIndex() + 1;
		Object[] partitions = list.getData();
		
		if (partitions != null) {
		
			do {
				int divisionCount = partitionCount / TAIL_SIZE;
				
				Object[] divisions = new Object[
				                                ((double)partitionCount / TAIL_SIZE) > divisionCount ? 
				                                		divisionCount + 1 :
				                                		divisionCount
				                               ];
			
				int k = 0;
			
				for (int i=0; i < divisions.length; i++) {
					int divisionSize = partitionCount - k > TAIL_SIZE ? TAIL_SIZE : partitionCount - k;
					
					divisions[i] = new Object[divisionSize];
							
					System.arraycopy(partitions, k, (Object[])divisions[i], 0, divisionSize);
					k += divisionSize;
					
				}
				
				level++;
				
				partitions = divisions;
				partitionCount = partitions.length;
				
			} while (partitionCount > 1);
		}
			
		data = partitions;
		index = list.size() - 1;
		
		tail = Arrays.copyOf(list.getTail(), list.getTail().length);
		
		tailIndex = list.getTailIndex();
		
		list.staleAll();
		
	}
	
	public BitmappedTrie(List<E> list) {
		this();
		
//		level = 0;
		
		int listSize = list.size();
		
		int partitionCount = listSize / TAIL_SIZE;
		int partitionCopyCount = 
				listSize % TAIL_SIZE == 0 ? partitionCount - 1 : partitionCount;
		
		for (int i = 0; i < partitionCopyCount; i++) {
			
			addTail(list.subList(i * TAIL_SIZE, (i * TAIL_SIZE) + TAIL_SIZE).toArray());
		}
		
		Object[] newTail = list.subList(
				partitionCopyCount * TAIL_SIZE, 
				partitionCopyCount * TAIL_SIZE + listSize - partitionCopyCount * TAIL_SIZE)
					.toArray();
	
		tail = new Object[TAIL_SIZE];
		
		System.arraycopy(newTail, 0, tail, 0, newTail.length);
		
		tailIndex = newTail.length - 1;
		index += newTail.length;
			
	}
	
	private BitmappedTrie(final Object[] oldData, 
							final Object[] oldTail, 
							final int oldTailIndex, 
							final int oldLevel, 
							final int oldIndex, 
							final boolean duplicate,
							final boolean oldCanShareData,
							final E e) {
		
		stale = new AtomicBoolean(false);
		
		initializeAndAddElement(oldData, oldTail, 
									oldTailIndex, oldLevel, oldIndex, 
									duplicate, oldCanShareData, 
									e);
	}

	private void initializeAndAddElement(final Object[] oldData, final Object[] oldTail,
			final int oldTailIndex, final int oldLevel, final int oldIndex,
			final boolean duplicate, final boolean oldCanShareData,
			final E e) {
		
		index = oldIndex + 1;
		tailIndex = oldTailIndex + 1;
		level = oldLevel;
		
		if (tailIndex < TAIL_SIZE) {
			
			data = oldData;
			
			if (duplicate) {
				tail = new Object[TAIL_SIZE];
				System.arraycopy(oldTail, 0, tail, 0, tailIndex + 1);
				canShareData = false;
			}
			else {
				tail = oldTail;
				canShareData = oldCanShareData;
			}
			
		}
		else {
			if (oldData.length == 0) {		// TODO: change this 
				
				data = new Object[TAIL_SIZE];   	// TODO: change this 
				data[0] = oldTail;
				
				tail = new Object[TAIL_SIZE];
				tailIndex = 0;
				
				level++;
				
				canShareData = true;
			}
			else {
				
				connectTail(oldData, oldTail, !duplicate && oldCanShareData);
				
				canShareData = true;
				
				tail = new Object[TAIL_SIZE];
				tailIndex = 0;
			}
		}
		
		tail[tailIndex] = e;
	}
	
	@SuppressWarnings("unchecked")
	private <V> void traverse(Object[] levelData, int currentLevel, Passable<V> visitor) {
		
		if (levelData == null) {
			return;
		}
		
		if (currentLevel == level) {
			for (Object dataItem : levelData) {
				for (Object element : (Object[])dataItem) {
					visitor.call((V)element);
				}
			}
		}
		else {
			for (Object dataItem : levelData) {
				traverse((Object[]) dataItem, currentLevel + 1, visitor);
			}
		}
	}
	
	public String toString() {
		
		final StringBuilder builder = new StringBuilder("[");
		
		traverse(data, 0, new Passable<E>() {

			public E call(E value) {
				if (builder.length() == 1) {
					builder.append(value);
				}
				else {
					builder.append(", ");
					builder.append(value);
				}

				return null;
			}
	
		});
		
		for (int i=0; i <= tailIndex ; i++) {
			if (builder.length() == 1) {
				builder.append(tail[i]);
			}
			else {
				builder.append(", ");
				builder.append(tail[i]);
			}
		}
		
		builder.append("]");
		
		return builder.toString();
		
	}
	
	
	
	private void connectTail(final Object[] oldData, final Object[] oldTail,
								final boolean mutateData) {
		
		Object[][] stack = new Object[level+1][];  // THIS IS A BIT BAD BECAUSE IT DEPENDS ON level that must be set before here
		stack[0] = oldData;
		
		int[] stackLenght = new int[level+1];      // THE SAME HERE
		stackLenght[0] = (((index - TAIL_SIZE - 1) >>> ((level + 1) * POWER)) & LOCATOR) + 1;
		
		for (int i = 1, l = level; i <= level; i++, l--) {
			//stack[i] = (Object[]) stack[i-1][stack[i-1].length-1];
			stack[i] = (Object[]) stack[i-1][stackLenght[i-1]-1];
			stackLenght[i] = (((index - TAIL_SIZE - 1) >>> ((l) * POWER)) & LOCATOR) + 1;
		}
		
		if (stackLenght[level] < TAIL_SIZE) {
		
			
			Object[] lastLevel;
			
			if (mutateData) {
				lastLevel = stack[level];
			}
			else {
				lastLevel = new Object[TAIL_SIZE];
				System.arraycopy(stack[level], 0, lastLevel, 0, stackLenght[level]);
			}
			
			lastLevel[stackLenght[level]] = oldTail;
			stack[level] = lastLevel;
				
			for (int i=level-1; i >= 0; i--) {
				Object[] newLevel;
				
				if (mutateData) {
					newLevel = stack[i];
				}
				else {
					newLevel = new Object[TAIL_SIZE];
					System.arraycopy(stack[i], 0, newLevel, 0, stackLenght[i]);
				}
				
				newLevel[stackLenght[i]-1] = stack[i+1];
				stack[i] = newLevel;
			}
			
			data = stack[0];

		}
		else {
			
			Object[] lastLevel = new Object[TAIL_SIZE];
			lastLevel[0] = oldTail;
			stack[level] = lastLevel;
			
			int k = level - 1;					
			while (k >= 0 && stackLenght[k] >= TAIL_SIZE) {
				Object[] newLevel = new Object[TAIL_SIZE];
				newLevel[0] = stack[k+1];
				stack[k] = newLevel;
				k--;
			}
			
			if (k < 0) {
				data = new Object[TAIL_SIZE];
				data[0] = oldData;
				data[1] = stack[0];
				
				level++;
			}
			else {
				
				Object[] joinLevel;
				
				if (mutateData) {
					joinLevel = stack[k];
				}
				else {
					joinLevel = new Object[TAIL_SIZE];
					System.arraycopy(stack[k], 0, joinLevel, 0, stackLenght[k]);
				}
				joinLevel[stackLenght[k]] = stack[k+1];
				stack[k] = joinLevel;
				
				for (int i=k-1; i >= 0; i--) {
					Object[] newLevel;
					
					if (mutateData) {
						newLevel = stack[i];
					}
					else {
						newLevel = new Object[TAIL_SIZE];
						System.arraycopy(stack[i], 0, newLevel, 0, stackLenght[i]);
					}
					newLevel[stackLenght[i] - 1] = stack[i+1];
					stack[i] = newLevel;
				}
				
				data = stack[0];
			}
			
		}
	}
	
	public BitmappedTrie<E> addElement(E e) {
		
//		return new BitmappedTrie<E>(data, tail, tailIndex, level, index, true, canShareData, e);
		
		//boolean duplicate = !stale.get() && !stale.compareAndSet(false, true);
		
		if (stale.getAndSet(true)) {
			return new BitmappedTrie<E>(data, tail, tailIndex, level, index, true, canShareData, e);
		}
		else {
			return new BitmappedTrie<E>(data, tail, tailIndex, level, index, false, canShareData, e);
		}

	}
	
	@SuppressWarnings("unchecked")
	public E getElement(int index) {
		
		if (index > this.index || index < 0) {
			throw new ArrayIndexOutOfBoundsException();
		}
		
		if (index > this.index - tailIndex - 1) {
			return (E) tail[index & LOCATOR];
		}
		
		Object[] location = (Object[]) data[(index >>> ((level + 1) * POWER)) & LOCATOR];
		
		for (int i = level; i > 0; i--) {
			location = (Object[]) location[(index >>> (i * POWER)) & LOCATOR];
		}
		
		E e = (E)location[index & LOCATOR];
		
		return e;
		
	}
	
	public int size() {
		return index < 0 ? 0 : index;
	}
	
	
	@SuppressWarnings("unchecked")
	public E lastAdded() {
		if (tailIndex < 0) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return (E)tail[tailIndex];
	}
	
	public static void main(String[] args) {
		
		BitmappedTrie<Integer> trie5 = new BitmappedTrie<Integer>(Arrays.asList(0));
		
		if (0 != trie5.lastAdded()) {
			throw new IllegalStateException();
		}
		
		for (int i=1; i < TEST_SIZE; i++) {
			trie5 = trie5.addElement(i);
			
			if (i != trie5.lastAdded()) {
				throw new IllegalStateException();
			}
		}
		
		BitmappedTrie<Integer> trie7 = new BitmappedTrie<Integer>();
		
		for (int i=0; i < TEST_SIZE; i++) {
			trie7 = trie7.addElement(i);
		}
		
		//System.out.println(trie7.toString());
		

		
		
		FastArrayList<Integer> fastList = new FastArrayList<Integer>();
		
		for (int i=0; i < TEST_SIZE; i++) {
			fastList.addElement(i);
		}
		
		BitmappedTrie<Integer> trie3 = new BitmappedTrie<Integer>(fastList);
		
		for (int i=0; i < TEST_SIZE; i++) {
			if (i != trie3.getElement(i)) {
				throw new IllegalStateException();
			}
		}

		BitmappedTrie<Integer> trie4 = new BitmappedTrie<Integer>();
		
		for (int i=0; i < TEST_SIZE; i++) {
			trie4 = trie4.addElement(i);
			
			if (i != trie4.lastAdded()) {
				throw new IllegalStateException();
			}
		}
		
		
		BitmappedTrie<Integer> trie2 = new BitmappedTrie<Integer>();
		
		for (int i=0; i < TEST_SIZE; i++) {
			trie2 = trie2.addElement(i);
		}
		
		for (int i=0; i < TEST_SIZE; i++) {
			if (i != trie2.getElement(i)) {
				throw new IllegalStateException();
			}
		}
		
		List<Integer> list = new ArrayList<Integer>();
		
		for (int i=0; i < TEST_SIZE; i++) {
			list.add(i);
		}
		
		BitmappedTrie<Integer> trie1 = new BitmappedTrie<Integer>(list);
		
		for (int i=0; i < TEST_SIZE; i++) {
			if (i != trie1.getElement(i)) {
				throw new IllegalStateException();
			}
		}
		
		
		
	}

}
