package me.test.playground;

import java.util.concurrent.atomic.AtomicBoolean;

import me.test.util.concurrent.ParallelRunner;
import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest17 {

	private static int TEST_SIZE = 100000000;
	
	private final AtomicBoolean lock = new AtomicBoolean(false);
	
	
	private boolean flag = false;
	
	private void test() {
		
		while (lock.compareAndSet(false, true)) {
		}
		
		if (flag == false) {
			flag = true;
		}
		
		while (lock.compareAndSet(true, false)) {
		}
	}
	
	public static void main(String[] args) {
		
		final SynchronizedOverheadTest17 test = new SynchronizedOverheadTest17();
				
		ParallelRunner.run(8, new Runnable() {

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
