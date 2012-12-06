package me.test.playground;

import me.test.util.concurrent.ParallelRunner;
import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest18 {

	private static int TEST_SIZE = 100000000;
	
	private boolean flag = false;
	
	private synchronized void test() {
		
		if (flag == false) {
			flag = true;
		}
	}
	
	public static void main(String[] args) {
		
		final SynchronizedOverheadTest18 test = new SynchronizedOverheadTest18();
				
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
