package me.test.playground;

import me.test.util.debug.CountTime;

public class ObjectCreationOverhead01 {
	
	private static int TEST_SIZE = 1000000000;
	
	private volatile int count = 0;
	
	private ObjectCreationOverhead01 increment() {
		
		count++;
		
		return this;
	}
	
	private int value() {
		return count;
	}

	public static void main(String[] args) {
		
		CountTime.run(new Runnable() {

			public void run() {
				
				ObjectCreationOverhead01 counter = new ObjectCreationOverhead01();
				
				for (int i=0; i < TEST_SIZE; i++) {
					counter = counter.increment();
				}
				
			}
			
		});
		
	}
}
