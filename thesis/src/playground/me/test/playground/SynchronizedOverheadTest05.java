package me.test.playground;

import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest05 {

	private static int TEST_SIZE = 100000000;
	
	private Thread creatorThread = Thread.currentThread();
	
	private void test() {
		
		if (!creatorThread.equals(Thread.currentThread())) {
			throw new IllegalAccessError();
		}
	}
	
	public static void main(String[] args) {
		
		final SynchronizedOverheadTest05 test = new SynchronizedOverheadTest05();
		
		CountTime.run(new Runnable() {

			public void run() {
				
				for (int i=0; i < TEST_SIZE; i++) {
					test.test();
				}
			}
			
		});
		
		
	}
}
