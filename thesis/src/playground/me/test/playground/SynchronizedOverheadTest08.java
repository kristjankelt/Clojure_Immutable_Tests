package me.test.playground;

import java.util.concurrent.atomic.AtomicIntegerArray;

import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest08 {

	private static int TEST_SIZE = 100000000;
	
	private AtomicIntegerArray flags;
	
	private void testWrite() {
		
		flags = new AtomicIntegerArray(TEST_SIZE);
		
		for (int i=0; i < TEST_SIZE; i++) {
			flags.set(i, 1);
		}
	}

	public static void main(String[] args) {
		
		final SynchronizedOverheadTest08 test = new SynchronizedOverheadTest08();
		
		CountTime.run(new Runnable() {

			public void run() {
				
				test.testWrite();
			}
			
		});
		
		
	}
}
