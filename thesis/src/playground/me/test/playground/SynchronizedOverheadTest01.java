package me.test.playground;

import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest01 {

	private static int TEST_SIZE = 100000000;
	
	private boolean flag;
	
	private void test() {
		flag = true;
	}
	
	private boolean readFlag() {
		return flag;
	}
	
	public static void main(String[] args) {
		
		final SynchronizedOverheadTest01 test = new SynchronizedOverheadTest01();
		
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
