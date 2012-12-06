package me.test.test.lowlevel;


public class ArrayCopyTest01 implements LowLevelTest {
	
	public String getGroupName() {
		return "arrayCopy01";
	}
	
	private int[] array11;

	public void prepare1(int testSize) {
		
		array11 = new int[32];
	}
	
	public void test1(int testSize) {
		
		int[] array12 = new int[32];
		
		System.arraycopy(array11, 0, array12, 0, array11.length);
	}
	
	private int[][] array21;

	
	public void prepare2(int testSize) {
		
		array21 = new int[2][4];
		
	}

	public void test2(int testSize) {
		
		int[][] array22 = new int[8][];
		
		array22[0] = array21[0];
		array22[1] = array21[1];

	}
}
