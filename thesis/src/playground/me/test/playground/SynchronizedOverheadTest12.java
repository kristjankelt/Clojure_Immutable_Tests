package me.test.playground;

import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest12 {

	private static int TEST_SIZE = 100000000;
	
	private Object[] tail = new Object[0];
	private int tailIndex = 0;
	
	private void test() {

		Object[] newTail = new Object[32];
		System.arraycopy(tail, 0, newTail, 0, tail.length);
		newTail[tailIndex++] = Integer.valueOf(1);
		if (tailIndex == 32) {
			tailIndex = 0;
		}
		tail = newTail;
		
	}
	
	public static void main(String[] args) {
		
		final SynchronizedOverheadTest12 test = new SynchronizedOverheadTest12();
		
		CountTime.run(new Runnable() {

			public void run() {
				
				for (int i=0; i < TEST_SIZE; i++) {
					test.test();
				}
			}
			
		});
		
	}
}
