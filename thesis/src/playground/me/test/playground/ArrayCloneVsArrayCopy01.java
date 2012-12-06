package me.test.playground;

import me.test.util.debug.CountTime;

public class ArrayCloneVsArrayCopy01 {
	
	private static int TEST_SIZE = 100000000;
	
	private Object[] data = new Object[32];
	
	private void test() {
		
		data = data.clone();
	}

	public static void main(String[] args) {
			
		final ArrayCloneVsArrayCopy01 test = new ArrayCloneVsArrayCopy01();
		
		CountTime.run(new Runnable() {

			public void run() {
				
				for (int i=0; i < TEST_SIZE; i++) {
					test.test();
				}
			}
			
		});
	}
}
