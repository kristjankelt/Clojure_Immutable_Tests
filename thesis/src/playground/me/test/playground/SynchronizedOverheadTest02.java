package me.test.playground;

import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest02 {

	private static int TEST_SIZE = 100000000;
	
	private boolean flag;
	
	private synchronized void test() {
		flag = true;
	}
	
	private synchronized boolean readFlag() {
		return flag;
	}
	
	private synchronized boolean readAndWrite() {
		boolean previousFlag = flag;
		flag = true;
		return previousFlag;
	}
	
	public static void main(String[] args) {
		
		final SynchronizedOverheadTest02 test = new SynchronizedOverheadTest02();
		
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
		
		CountTime.run(new Runnable() {

			public void run() {
				
				for (int i=0; i < TEST_SIZE; i++) {
					@SuppressWarnings("unused")
					boolean value = test.readAndWrite();
				}
			}
			
		});
	}
}
