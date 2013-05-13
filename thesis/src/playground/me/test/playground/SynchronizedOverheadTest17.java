package me.test.playground;

import java.util.concurrent.atomic.AtomicBoolean;

import me.test.util.concurrent.ParallelRunner;
import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest17 {

	private final static int TEST_SIZE = 1000000000;
	
	private final static int THREADS = 1;
		
	private final AtomicBoolean lock = new AtomicBoolean(false);
	
	
	private boolean flag = false;
	
	private synchronized void test() {
		
		if (flag == false) {
			flag = true;
		}

	}
	
	public static void main(String[] args) {
		
		final SynchronizedOverheadTest17 test = new SynchronizedOverheadTest17();
				
		ParallelRunner.run(THREADS, new Runnable() {

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
