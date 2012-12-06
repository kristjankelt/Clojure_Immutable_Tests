package me.test.playground;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import me.test.util.concurrent.ParallelRunner;
import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest19 {

	private static int TEST_SIZE = 100000000;
	
	private final Lock lock = new ReentrantLock();
	
	
	private boolean flag = false;
	
	private void test() {
		
		lock.lock();
		
		try {
			
			if (flag == false) {
				flag = true;
			}
		}
		finally {
			lock.unlock();
		}
		
	}
	
	public static void main(String[] args) {
		
		final SynchronizedOverheadTest19 test = new SynchronizedOverheadTest19();
				
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
