package me.test.playground;

import java.util.concurrent.atomic.AtomicBoolean;

import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest06 {

	private static int TEST_SIZE = 100000000;
	
	private final AtomicBoolean flag = new AtomicBoolean();
	
	private void test() {
		flag.set(true);
	}
	
	private boolean readFlag() {
		return flag.get();
	}
	
	private boolean readAndWrite() {
		return flag.getAndSet(true);
	}
	
	public static void main(String[] args) {
		
		final SynchronizedOverheadTest06 test = new SynchronizedOverheadTest06();
		
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
