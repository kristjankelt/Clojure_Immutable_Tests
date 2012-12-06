package me.test.playground;

import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest07 {

	private static int TEST_SIZE = 100000000;
	
	private volatile boolean flags[];
	
	private void testWrite() {
		
		boolean flags[] = new boolean[TEST_SIZE];
		
		for (int i=0; i < TEST_SIZE; i++) {
			flags[i] = true;
		}
		
		this.flags = flags;
	}
	
	private void testRead() {
		
		for (int i=0; i < TEST_SIZE; i++) {
			@SuppressWarnings("unused")
			boolean flag = flags[i];
		}
	
	}

	public static void main(String[] args) {
		
		final SynchronizedOverheadTest07 test = new SynchronizedOverheadTest07();
		
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
