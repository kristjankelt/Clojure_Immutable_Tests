package me.test.playground;

import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest09 {

	private static int TEST_SIZE = 100000000;
	
	private boolean flags[] = new boolean[TEST_SIZE];
	
	private void testWrite() {
		
		
		for (int i=0; i < TEST_SIZE; i++) {
			flags[i] = true;
		}
		
	}
	
	private void testRead() {
		
		
		for (int i=0; i < TEST_SIZE; i++) {
			@SuppressWarnings("unused")
			boolean flag = flags[i];
		}
		
	}

	public static void main(String[] args) {
		
		final SynchronizedOverheadTest09 test = new SynchronizedOverheadTest09();
		
		CountTime.run(new Runnable() {

			public void run() {
				
				test.testWrite();
			}
			
		});
		
		CountTime.run(new Runnable() {

			public void run() {
				
				test.testRead();
			}
			
		});
		
	}
}
