package me.test.util;


public class BitmappedTrieBasic<E> {

	private static final int TEST_SIZE = 36;
	
	private final static int POWER = 5;
	
	private final static int TAIL_SIZE = (int) Math.pow(2, POWER);
	
	private final static int LOCATOR = (int) Math.pow(2, POWER)-1;


	private Object[] data;
	private Object[] tail;
	private int tailIndex;
	private int index;
	private int level;
	
	public BitmappedTrieBasic() {
		this.data = new Object[0];
		this.tail = new Object[0];
		tailIndex = -1;
		index = -1;
		level = -1;
	}
	
	private BitmappedTrieBasic(final Object[] oldData, 
							final Object[] oldTail, 
							final int oldTailIndex, 
							final int oldLevel, 
							final int oldIndex, 
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
			else 
			{
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
					
					tail = new Object[1];
					tailIndex = 0;
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
					
					tail = new Object[1];
					tailIndex = 0;
					
				}
			}
		}
		
		tail[tailIndex] = e;
	}
	
	public BitmappedTrieBasic<E> addElement(E e) {
		
		return new BitmappedTrieBasic<E>(data, tail, tailIndex, level, index, e);

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
		
		BitmappedTrieBasic<Integer> trie = new BitmappedTrieBasic<Integer>();
		
		for (int i=0; i < TEST_SIZE; i++) {
			trie = trie.addElement(i);
		}
		
		for (int i=0; i < TEST_SIZE; i++) {
			if (i != trie.getElement(i)) {
				throw new IllegalStateException();
			}
		}
		
		
	}
}
