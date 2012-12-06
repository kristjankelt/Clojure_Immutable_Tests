package me.test.playground;

import java.util.concurrent.atomic.AtomicBoolean;

import me.test.util.concurrent.ParallelRunner;
import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest15 {

	private static int TEST_SIZE = 100000000;
	
	private final AtomicBoolean flag = new AtomicBoolean(false);
	
	private void test() {
		@SuppressWarnings("unused")
		boolean result = flag.compareAndSet(false, true);
	}
	
	public static void main(String[] args) {
		
		final SynchronizedOverheadTest15 test = new SynchronizedOverheadTest15();
				
		ParallelRunner.run(4, new Runnable() {

			public void run() {
				
				CountTime.run(new Runnable() {

					public void run() {
						for (int i=0; i < TEST_SIZE; i++) {
							test.test();
						}
					}
				});
			}
			
		});
	
		
	}
}
