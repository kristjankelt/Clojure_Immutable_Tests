package me.test.playground;

import java.util.concurrent.atomic.AtomicInteger;

import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest04 {

	private static int TEST_SIZE = 100000000;
	
	private final AtomicInteger version = new AtomicInteger(0);
	
	private void test() {
		version.incrementAndGet();
	}
	
	private int readVersion() {
		return version.get();
	}
	
	public static void main(String[] args) {
		
		final SynchronizedOverheadTest04 test = new SynchronizedOverheadTest04();
		
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
					int value = test.readVersion();
				}
			}
			
		});
		
	}
}
