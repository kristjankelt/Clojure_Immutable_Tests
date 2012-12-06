package me.test.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import me.test.transactions.Passable;


public class BitmappedTrieOld2<E> {

	private static final int TEST_SIZE = 320;
	
	private final static int POWER = BitmappedTrie.POWER;
	
	final static int TAIL_SIZE = (int) Math.pow(2, POWER);
	
	private final static int LOCATOR = (int) Math.pow(2, POWER)-1;


	private Object[] data;
	private Object[] tail;
	private int tailIndex;
	private int index;
	private int level;
	private boolean stale;
	
	public BitmappedTrieOld2() {
		this.data = new Object[0];
		this.tail = new Object[TAIL_SIZE];
		tailIndex = -1;
		index = -1;
		level = -1;
		stale = false;
	}
	
	private void addTail(Object[] newTail) {
		
		if (tailIndex != -1) {
			throw new IllegalStateException();
		}
		
		index += newTail.length;
		
		if (data.length == 0) {			// TODO: change this 
			
			data = new Object[1];   	// TODO: change this 
			data[0] = newTail;
			level++;
		}
		else {
			connectTail(data, newTail);
		}

	}
	
	public BitmappedTrieOld2(FastArrayList<E> list) {
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
		
		tail = Arrays.copyOf(list.getTail(), TAIL_SIZE);
		
		tailIndex = list.getTailIndex();
		
		list.staleAll();
		
	}
	
	public BitmappedTrieOld2(List<E> list) {
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
	
	private BitmappedTrieOld2(final Object[] oldData, 
							final Object[] oldTail, 
							final int oldTailIndex, 
							final int oldLevel, 
							final int oldIndex, 
							final E e) {
		
		stale = false;
		
		initializeAndAddElement(oldData, oldTail, oldTailIndex, oldLevel, oldIndex, e);
	}

	private void initializeAndAddElement(final Object[] oldData, final Object[] oldTail,
			final int oldTailIndex, final int oldLevel, final int oldIndex,
			final E e) {
		
		index = oldIndex + 1;
		tailIndex = oldTailIndex + 1;
		level = oldLevel;
		
		if (tailIndex < TAIL_SIZE) {
			
			data = oldData;
			tail = oldTail;
//			tail = new Object[oldTail.length + 1];
//			System.arraycopy(oldTail, 0, tail, 0, oldTail.length);
			
		}
		else {
			if (oldData.length == 0) {
				
				data = new Object[1];
				data[0] = oldTail;
				
				tail = new Object[TAIL_SIZE];
				tailIndex = 0;
				
				level++;
			}
			else {
				
				connectTail(oldData, oldTail);
				
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
	
	
	
	private void connectTail(final Object[] oldData, final Object[] oldTail) {
		
		Object[][] stack = new Object[level+1][];
		stack[0] = oldData;
		
		for (int i = 1; i <= level; i++) {
			stack[i] = (Object[]) stack[i-1][stack[i-1].length-1];
		}
		
		if (stack[level].length < TAIL_SIZE) {
			
			Object[] lastLevel = new Object[stack[level].length + 1];
			System.arraycopy(stack[level], 0, lastLevel, 0, stack[level].length);
			lastLevel[lastLevel.length - 1] = oldTail;
			stack[level] = lastLevel;
				
			for (int i=level-1; i >= 0; i--) {
				Object[] newLevel = new Object[stack[i].length];
				System.arraycopy(stack[i], 0, newLevel, 0, stack[i].length);
				stack[i][stack[i].length-1] = stack[i+1];
			}
			
			data = stack[0];

		}
		else {
			
			Object[] lastLevel = new Object[1];
			lastLevel[0] = oldTail;
			stack[level] = lastLevel;
			
			int k = level - 1;					
			while (k >= 0 && stack[k].length >= TAIL_SIZE) {
				Object[] newLevel = new Object[1];
				newLevel[0] = stack[k+1];
				stack[k] = newLevel;
				k--;
			}
			
			if (k < 0) {
				data = new Object[2];
				data[0] = oldData;
				data[1] = stack[0];
				
				level++;
			}
			else {
				
				Object[] joinLevel = new Object[stack[k].length + 1];
				System.arraycopy(stack[k], 0, joinLevel, 0, stack[k].length);
				joinLevel[joinLevel.length - 1] = stack[k+1];
				stack[k] = joinLevel;
				
				for (int i=k-1; i >= 0; i--) {
					Object[] newLevel = new Object[stack[i].length];
					System.arraycopy(stack[i], 0, newLevel, 0, stack[i].length);
					newLevel[newLevel.length - 1] = stack[i+1];
					stack[i] = newLevel;
				}
				
				data = stack[0];
			}
			
		}
	}
	
	public BitmappedTrieOld2<E> addElement(E e) {
		
		if (stale) {
			
			Object[] newTail = new Object[TAIL_SIZE];
			System.arraycopy(tail, 0, newTail, 0, tailIndex + 1);
			
			return new BitmappedTrieOld2<E>(data, newTail, tailIndex, level, index, e);
		}
		else {
			stale = true;
			
			return new BitmappedTrieOld2<E>(data, tail, tailIndex, level, index, e);
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
		
		BitmappedTrieOld2<Integer> trie7 = new BitmappedTrieOld2<Integer>();
		
		for (int i=0; i < TEST_SIZE; i++) {
			trie7 = trie7.addElement(i);
		}
		
		//System.out.println(trie7.toString());
		

		BitmappedTrieOld2<Integer> trie5 = new BitmappedTrieOld2<Integer>(Arrays.asList(0));
		
		if (0 != trie5.lastAdded()) {
			throw new IllegalStateException();
		}
		
		for (int i=1; i < TEST_SIZE; i++) {
			trie5 = trie5.addElement(i);
			
			if (i != trie5.lastAdded()) {
				throw new IllegalStateException();
			}
		}
		
		FastArrayList<Integer> fastList = new FastArrayList<Integer>();
		
		for (int i=0; i < TEST_SIZE; i++) {
			fastList.addElement(i);
		}
		
		BitmappedTrieOld2<Integer> trie3 = new BitmappedTrieOld2<Integer>(fastList);
		
		for (int i=0; i < TEST_SIZE; i++) {
			if (i != trie3.getElement(i)) {
				throw new IllegalStateException();
			}
		}

		BitmappedTrieOld2<Integer> trie4 = new BitmappedTrieOld2<Integer>();
		
		for (int i=0; i < TEST_SIZE; i++) {
			trie4 = trie4.addElement(i);
			
			if (i != trie4.lastAdded()) {
				throw new IllegalStateException();
			}
		}
		
		
		BitmappedTrieOld2<Integer> trie2 = new BitmappedTrieOld2<Integer>();
		
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
		
		BitmappedTrieOld2<Integer> trie1 = new BitmappedTrieOld2<Integer>(list);
		
		for (int i=0; i < TEST_SIZE; i++) {
			if (i != trie1.getElement(i)) {
				throw new IllegalStateException();
			}
		}
		
		
		
	}

}
