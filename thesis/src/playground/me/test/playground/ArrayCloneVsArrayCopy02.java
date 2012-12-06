package me.test.playground;

import me.test.util.debug.CountTime;

public class ArrayCloneVsArrayCopy02 {
	
	private static int TEST_SIZE = 100000000;
	
	private Object[] data = new Object[32];
	
	private void test() {
	
		Object[] newData = new Object[32];
		System.arraycopy(data, 0, newData, 0, data.length);
		data = newData;
	}

	public static void main(String[] args) {
			
		final ArrayCloneVsArrayCopy02 test = new ArrayCloneVsArrayCopy02();
		
		CountTime.run(new Runnable() {

			public void run() {
				
				for (int i=0; i < TEST_SIZE; i++) {
					test.test();
				}
			}
			
		});
	}
}
