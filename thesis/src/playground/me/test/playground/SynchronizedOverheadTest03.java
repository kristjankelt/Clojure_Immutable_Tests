package me.test.playground;

import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest03 {

	private static int TEST_SIZE = 100000000;
	
	private volatile boolean flag;
	
	private void test() {
		flag = true;
	}
	
	private boolean readFlag() {
		return flag;
	}
	
	public static void main(String[] args) {
		
		final SynchronizedOverheadTest03 test = new SynchronizedOverheadTest03();
		
		CountTime.run(new Runnable() {

			public void run() {
				
				for (int i=0; i < TEST_SIZE; i++) {
					test.test();
				}
			}
			
		});
		
		CountTime.run(new Runnable() {

			public void run() {
				
				for (int i=0; i < TEST_SIZE; i++) {
					@SuppressWarnings("unused")
					boolean value = test.readFlag();
				}
			}
			
		});
	}
}
