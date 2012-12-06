package me.test.playground;

public class SimpleArrayList {

	private int[] data;
	private int[] tail;
	private int index;
	
	public SimpleArrayList() {
		this.data = new int[0];
		this.data = new int[0];
		index = -1;
	}
	
	public SimpleArrayList addElement(int e) {
		
		index++;
		
		if (index+1 > data.length) {
			System.out.println("Extend!");
			int[] newData = new int[(int) ((data.length+1) * 1.5)];
			
			System.arraycopy(data, 0, newData, 0, data.length);
			
			data = newData;
		}
		
		data[index] = e;
		
		return this;
	}
	
	public int getElement(int index) {
		if (index > this.index) {
			throw new IndexOutOfBoundsException();
		}
		
		return data[index];
	}
	
	public String toString() {
		if (data.length == 0) {
			return "[]";
		}
		
		StringBuilder builder = new StringBuilder("[");
		
		int i = 0;
		
		for (int e : data) {
			if (i == 0) {
				i++;
			}
			else {
				builder.append(", ");
			}
			
			builder.append(e);
		}
		
		builder.append("]");
		
		return builder.toString();
	}
	
	public static void main(String[] args) {
		
		SimpleArrayList trie = new SimpleArrayList();
		
		for (int i=0; i < 100; i++) {
			trie.addElement(i);
		}
		
		System.out.println(trie);
	}
}
