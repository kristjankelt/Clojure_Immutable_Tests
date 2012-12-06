package me.test.util;

import java.util.Arrays;


public class FastArrayList<E> {
	
	private final static int ARRAY_SIZE = 32000;

	private final static int TAIL_SIZE = BitmappedTrie.TAIL_SIZE;

	private static final int LENGHT_MULTIPLIER = 2;

	private Object[] data;
	private boolean[] stale;
	private Object[] tail;
	private int tailIndex;
	private int dataIndex;
	private int index;
	
	private Thread creatorThread;
	
	public FastArrayList() {
		this.tail = new Object[TAIL_SIZE];
		tailIndex = -1;
		index = -1;
		dataIndex = -1;
		
		creatorThread = Thread.currentThread();
	}
	
	public FastArrayList<E> addElement(E e) {
		
		if (creatorThread != Thread.currentThread()) {
			throw new IllegalAccessError();
		}
		
		index++;
		tailIndex++;
		
		if (tailIndex >= TAIL_SIZE) {
			
			dataIndex++;

			if (data == null) {
				data = new Object[2];
			}
			
			if (data.length <= dataIndex) {
				Object[] newData = new Object[data.length * LENGHT_MULTIPLIER];
				System.arraycopy(data, 0, newData, 0, data.length);
				data = newData;
			}

			data[dataIndex] = tail;
			
			tail = new Object[TAIL_SIZE];
			tailIndex = 0;
		}
		
		tail[tailIndex] = e;
		
		return this;
	}
	
	public FastArrayList<E> removeElement(int index) {
		
		if (creatorThread != Thread.currentThread()) {
			throw new IllegalAccessError();
		}
		
		int getDataIndex = index / TAIL_SIZE;
		int getTailIndex = index - getDataIndex * (TAIL_SIZE ) ;
		
		if (getDataIndex > dataIndex) {
			// (E)tail[getTailIndex];
			
			if (tailIndex >= 0) {
				if (getTailIndex < tailIndex) {
					System.arraycopy(tail, getTailIndex + 1, tail, getTailIndex, TAIL_SIZE - getTailIndex - 1);
				}
				tailIndex--;
			}
			else if (dataIndex >= 0) {
				tail = (Object[])data[dataIndex];
				data[dataIndex] = null;
				dataIndex--;
				tailIndex = TAIL_SIZE - 1;
			}
			else {
				throw new IndexOutOfBoundsException();
			}
		}
		else {
			
			// (E) ((Object[])data[getDataIndex])[getTailIndex];
			
			Object[] newDataItem = new Object[TAIL_SIZE];
			
			
			System.arraycopy(data[getDataIndex], 0, newDataItem, 0, getTailIndex);
			
			if (getTailIndex < TAIL_SIZE - 1) {
				System.arraycopy(data[getDataIndex], getTailIndex + 1, newDataItem, getTailIndex, TAIL_SIZE - getTailIndex - 1);
			}
			
			int k = getDataIndex;
			
			while (k < dataIndex) {
				k++;
				
				Object[] nextDataItem = (Object[])data[k];
				newDataItem[TAIL_SIZE -1] = nextDataItem[0];
				
				data[k-1] = newDataItem;
				
				newDataItem = new Object[TAIL_SIZE];
				
				System.arraycopy(data[k], 1, newDataItem, 0, TAIL_SIZE - 1);
				
			}
			
			if (tailIndex >= 0) {
				newDataItem[TAIL_SIZE -1] = tail[0];
				data[k] = newDataItem;
				
				System.arraycopy(tail, 1, tail, 0, TAIL_SIZE - 1);
				tailIndex--;
			}
			else {
				tail = newDataItem;
				data[k] = null;
				dataIndex--;
				tailIndex = TAIL_SIZE - 2;
			}
			
		}
		
		this.index--;
		
		
		return this;
	}

	@SuppressWarnings("unchecked")
	public E getElement(int index) {
		
		if (index < 0 || index > this.index) {
			throw new IndexOutOfBoundsException();
		}
		
		int getDataIndex = index / TAIL_SIZE;
		int getTailIndex = index - getDataIndex * (TAIL_SIZE ) ;
		
		if (getDataIndex > dataIndex) {
			return (E)tail[getTailIndex];
		}
		else {
			return (E) ((Object[])data[getDataIndex])[getTailIndex];
		}
		
	}
	
	public FastArrayList<E> setElement(int index, E e) {
		
		if (creatorThread != Thread.currentThread()) {
			throw new IllegalAccessError();
		}
		
		if (index > this.index) {
			throw new IndexOutOfBoundsException();
		}
		
		int getDataIndex = index / TAIL_SIZE;
		int getTailIndex = index - getDataIndex * (TAIL_SIZE ) ;
		
		if (getDataIndex > dataIndex) {
			tail[getTailIndex] = e;
		}
		else {
			if (stale != null && getDataIndex < stale.length && stale[getDataIndex]) {
				Object[] newDataItem = new Object[TAIL_SIZE];
				
				System.arraycopy((Object[])data[getDataIndex], 0, newDataItem, 0, TAIL_SIZE);
				
				newDataItem[getTailIndex] = e;
				
				data[getDataIndex] = newDataItem;
				
				stale[getDataIndex] = false;
			}
			else {
				((Object[])data[getDataIndex])[getTailIndex] = e;
			}
		}
		
		return this;
	}
	
	public String toString() {
		return "[-]";
		
	}

	public int size() {

		return ((dataIndex + 1) * TAIL_SIZE) + (tailIndex +1);
	}
	
	public static void main(String[] args) {
		
		FastArrayList<Integer> array3 = new FastArrayList<Integer>();
		
		for (int i=0; i < ARRAY_SIZE; i++) {
			array3.addElement(i);
		}
		
		for (int i=0; i < ARRAY_SIZE; i++) {
			int index = array3.size() - 1; 
			if (array3.getElement(index) != index) {
				throw new IllegalStateException();
			}
			array3.removeElement(index);
			
		}
		
		FastArrayList<Integer> array2 = new FastArrayList<Integer>();
		
		for (int i=0; i < ARRAY_SIZE; i++) {
			array2.addElement(i);
		}
		
		for (int i=0; i < ARRAY_SIZE; i++) {
			if (array2.getElement(0) != i) {
				throw new IllegalStateException();
			}
			array2.removeElement(0);
			
		}
		
		
		
		FastArrayList<Integer> array = new FastArrayList<Integer>();
		
		for (int i=0; i < ARRAY_SIZE; i++) {
			array.addElement(i);
		}
		
		for (int i=0; i < ARRAY_SIZE; i++) {
			if (array.getElement(i) != i) {
				throw new IllegalStateException();
			}
		}
		
		for (int i=0; i < ARRAY_SIZE; i++) {
			array.setElement(i, 0);
		}
		
		for (int i=0; i < ARRAY_SIZE; i++) {
			if (array.getElement(i) != 0) {
				throw new IllegalStateException();
			}
		}
		
	}
	
	void staleAll() {
		boolean[] newStale = new boolean[dataIndex + 1];
		
		Arrays.fill(newStale, true);
		
		stale = newStale;
	}
	
	int getDataIndex() {
		return dataIndex;
	}
	
	int getTailIndex() {
		return tailIndex;
	}
	
	Object[] getData() {
		return data;
	}
	
	
	Object[] getTail() {
		return tail;
	}
}
