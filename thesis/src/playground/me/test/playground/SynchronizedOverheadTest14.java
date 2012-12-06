package me.test.playground;

import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest14 {

	private static int TEST_SIZE = 100000000;
	
	private Object[] tail = new Object[32];
	private int tailIndex = 0;
	
	private void test() {
		tail[tailIndex++] = Integer.valueOf(1);
		if (tailIndex == 32) {
			tail = new Object[32];
			tailIndex = 0;
		}
	}
	
	public static void main(String[] args) {
		
		final SynchronizedOverheadTest14 test = new SynchronizedOverheadTest14();
		
		CountTime.run(new Runnable() {

			public void run() {
				
				for (int i=0; i < TEST_SIZE; i++) {
					test.test();
				}
			}
			
		});
		
	}
}
