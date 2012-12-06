package me.test.test.lowlevel;


public class ArrayCopyTest03 implements LowLevelTest {
	
	public String getGroupName() {
		return "arrayCopy03";
	}
	
	private int[] array1;

	public void prepare1(int testSize) {
		
		array1 = new int[32];
	}
	
	public void test1(int testSize) {
		
		int[] array2 = new int[32];
		
		System.arraycopy(array1, 0, array2, 0, array1.length);
		
		array2[0] = 1;
	}
	
	public void prepare2(int testSize) {
		
		array1 = new int[32];
	}
	
	public void test2(int testSize) {
		
		array1[0] = 1;
	}
}
