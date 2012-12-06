package me.test.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CopyOfBitmappedTrie<E> {

	private static final int TEST_SIZE = 128;
	
	private final static int POWER = 5;
	
	private final static int TAIL_SIZE = (int) Math.pow(2, POWER);
	
	private final static int LOCATOR = (int) Math.pow(2, POWER)-1;


	private Object[] data;
	private Object[] tail;
	private int tailIndex;
	private int index;
	private int level;
	
	public CopyOfBitmappedTrie() {
		this.data = new Object[0];
		this.tail = new Object[0];
		tailIndex = -1;
		index = -1;
		level = -1;
	}
	
	private void addTail(Object[] newTail) {
		
		if (tail.length != 0) {
			throw new IllegalStateException();
		}
		
		index += newTail.length;
		
		connectTail(data, newTail);

	}
	
	public CopyOfBitmappedTrie(FastArrayList<E> list) {
		this();
		
		level = 0;
		
		for (Object partition :  list.getData()) {
			if (partition == null) {
				break;
			}
			addTail((Object[])partition);
		}
		
		tail = Arrays.copyOf(list.getTail(), list.getTail().length);
		
		tailIndex = tail.length - 1;
		index += tail.length;
	}
	
	public CopyOfBitmappedTrie(List<E> list) {
		this();
		
		level = 0;
		
		int listSize = list.size();
		
		int partitionCount = listSize / TAIL_SIZE;
		int partitionCopyCount = 
				listSize % TAIL_SIZE == 0 ? partitionCount - 1 : partitionCount;
		
		for (int i = 0; i < partitionCopyCount; i++) {
			
			addTail(list.subList(i * TAIL_SIZE, (i * TAIL_SIZE) + TAIL_SIZE).toArray());
		}
	
		tail = list.subList(
				partitionCopyCount * TAIL_SIZE, 
				partitionCopyCount * TAIL_SIZE + listSize - partitionCopyCount * TAIL_SIZE)
					.toArray();
		
		tailIndex = tail.length - 1;
		index += tail.length;
			
	}
	
//	public BitmappedTrie(List<E> list) {
//		this();
//		
//		level = 0;
//		
//		Object[] dataCopy = list.toArray();
//		
//		int partitionCount = dataCopy.length / TAIL_SIZE;
//		int partitionCopyCount = 
//				dataCopy.length % TAIL_SIZE == 0 ? partitionCount - 1 : partitionCount;
//		
//		for (int i = 0; i < partitionCopyCount; i++) {
//			Object[] partition = new Object[TAIL_SIZE];
//			
//			System.arraycopy(dataCopy, i * TAIL_SIZE, partition, 0, TAIL_SIZE);
//			
//			addTail(partition);
//		}
//	
//		tail = new Object[dataCopy.length - partitionCopyCount * TAIL_SIZE];
//		System.arraycopy(dataCopy, partitionCopyCount * TAIL_SIZE, tail, 0, dataCopy.length - partitionCopyCount * TAIL_SIZE);
//		tailIndex = tail.length - 1;
//		index += tail.length;
//			
//	}

	
	private CopyOfBitmappedTrie(final Object[] oldData, 
							final Object[] oldTail, 
							final int oldTailIndex, 
							final int oldLevel, 
							final int oldIndex, 
							final E e) {
		
		addElement(oldData, oldTail, oldTailIndex, oldLevel, oldIndex, e);
	}

	private void addElement(final Object[] oldData, final Object[] oldTail,
			final int oldTailIndex, final int oldLevel, final int oldIndex,
			final E e) {
		index = oldIndex + 1;
		tailIndex = oldTailIndex + 1;
		level = oldLevel;
		
		if (tailIndex < TAIL_SIZE) {
			
			data = oldData;
			
			tail = new Object[oldTail.length + 1];
			System.arraycopy(oldTail, 0, tail, 0, oldTail.length);
			
		}
		else {
			if (oldData.length == 0) {
				
				data = new Object[1];
				data[0] = oldTail;
				
				tail = new Object[1];
				tailIndex = 0;
				
				level++;
			}
			else {
				
				connectTail(oldData, oldTail);
				
				tail = new Object[1];
				tailIndex = 0;
			}
		}
		
		tail[tailIndex] = e;
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
					Object[] newLevel = new Object[stack[i].length + 1];
					System.arraycopy(stack[i], 0, newLevel, 0, stack[i].length);
					newLevel[newLevel.length - 1] = stack[i+1];
					stack[i] = newLevel;
				}
				
				data = stack[0];
			}
			
			
			
		}
	}
	
	public CopyOfBitmappedTrie<E> addElement(E e) {
		
		return new CopyOfBitmappedTrie<E>(data, tail, tailIndex, level, index, e);

	}
	
	@SuppressWarnings("unchecked")
	public E getElement(int index) {
		
		if (index > this.index || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		if (index > this.index - tail.length) {
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
	
	public String toString() {
		return "[-]";
		
	}
	
	public static void main(String[] args) {
		
		FastArrayList<Integer> fastList = new FastArrayList<Integer>();
		
		for (int i=0; i < TEST_SIZE; i++) {
			fastList.addElement(i);
		}
		
		CopyOfBitmappedTrie<Integer> trie3 = new CopyOfBitmappedTrie<Integer>(fastList);
		
		for (int i=0; i < TEST_SIZE; i++) {
			if (i != trie3.getElement(i)) {
				throw new IllegalStateException();
			}
		}
		
		
		CopyOfBitmappedTrie<Integer> trie2 = new CopyOfBitmappedTrie<Integer>();
		
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
		
		CopyOfBitmappedTrie<Integer> trie1 = new CopyOfBitmappedTrie<Integer>(list);
		
		for (int i=0; i < TEST_SIZE; i++) {
			if (i != trie1.getElement(i)) {
				throw new IllegalStateException();
			}
		}
		
		
		
	}
}
