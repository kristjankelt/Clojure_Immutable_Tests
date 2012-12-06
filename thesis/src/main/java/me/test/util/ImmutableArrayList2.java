package me.test.util;



public class ImmutableArrayList2<E> {
	
	private final static int ARRAY_SIZE = 32000;

	private final static int TAIL_SIZE = BitmappedTrie.TAIL_SIZE;

	private static final int LENGHT_MULTIPLIER = 2;

	private Object[] data;
	private Object[] tail;
	private int tailIndex;
	private int dataIndex;
	private int index;
	
	private boolean stale;
	private Thread creatorThread;
	
	public ImmutableArrayList2() {
		this.data = new Object[2];
		this.tail = new Object[TAIL_SIZE];
		tailIndex = -1;
		index = -1;
		dataIndex = -1;
		stale = false;
		creatorThread = Thread.currentThread();
	}
	
	private ImmutableArrayList2(Object[] oldData, Object[] oldTail, int oldIndex, int oldTailIndex, int oldDataIndex, E e, Thread oldCreatorThread) {
		
		stale = false;
		creatorThread = oldCreatorThread;
		
		index = oldIndex + 1;
		tailIndex = oldTailIndex + 1;
		
		data = oldData;
		dataIndex = oldDataIndex;
		
		if (tailIndex < TAIL_SIZE) {
			tail = oldTail;
		}
		else {
			dataIndex++;
			Object[] newData = newData();
			newData[dataIndex] = oldTail;
			data = newData;
			tail = new Object[TAIL_SIZE];
			tailIndex = 0;
		}
		
		tail[tailIndex] = e;
		
		
	}
	
	public ImmutableArrayList2<E> addElement(E e) {
		
		if (creatorThread != Thread.currentThread()) {
			Object[] newData = new Object[data.length];
			System.arraycopy(data, 0, newData, 0, dataIndex + 1);
			
			Object[] newTail = new Object[TAIL_SIZE];
			System.arraycopy(tail, 0, newTail, 0, tailIndex + 1);
				
			return new ImmutableArrayList2<E>(newData, newTail, index, tailIndex, dataIndex, e, Thread.currentThread());

		}
		
		if (stale) {
			
			Object[] newData = new Object[data.length];
			System.arraycopy(data, 0, newData, 0, dataIndex + 1);
			
			Object[] newTail = new Object[TAIL_SIZE];
			System.arraycopy(tail, 0, newTail, 0, tailIndex + 1);
				
			return new ImmutableArrayList2<E>(newData, newTail, index, tailIndex, dataIndex, e, creatorThread);
		}
		else {
			stale = true;
			
			return new ImmutableArrayList2<E>(data, tail, index, tailIndex, dataIndex, e, creatorThread);
		}
		
	}

	private Object[] newData() {

		if (data.length <= dataIndex + 1) {
			
			Object[] newData = new Object[data.length * LENGHT_MULTIPLIER];
	
			System.arraycopy(data, 0, newData, 0, data.length);
			
			return newData;
		}
		else {
			return data;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public E getElement(int index) {
		
		if (index > this.index) {
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
	
	public String toString() {
		return "[-]";
		
	}
	
	public static void main(String[] args) {
		
		ImmutableArrayList2<Integer> trie = new ImmutableArrayList2<Integer>();
		
		for (int i=0; i < ARRAY_SIZE; i++) {
			trie = trie.addElement(i);
		}
		
		for (int i=0; i < ARRAY_SIZE; i++) {
			if (trie.getElement(i) != i) {
				throw new IllegalStateException();
			}
		}
		
	}
	
	Object[] getData() {
		return data;
	}
	
	Object[] getTail() {
		return tail;
	}
}
