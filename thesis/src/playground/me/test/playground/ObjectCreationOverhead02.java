package me.test.playground;

import me.test.util.debug.CountTime;

public class ObjectCreationOverhead02 {
	
	private static int TEST_SIZE = 1000000000;
	
	private final int count;
	
	ObjectCreationOverhead02() {
		count = 0;
	}
	
	ObjectCreationOverhead02(int newCount) {
		count = newCount;
	}
	
	private ObjectCreationOverhead02 increment() {
		
		return new ObjectCreationOverhead02(count + 1);
	}
	
	private int value() {
		return count;
	}

	public static void main(String[] args) {
		
		CountTime.run(new Runnable() {

			public void run() {
				
				ObjectCreationOverhead02 counter = new ObjectCreationOverhead02();
				
				for (int i=0; i < TEST_SIZE; i++) {
					counter = counter.increment();
				}
				
				System.out.println(counter.value());
			}
			
		});
		
	}
}
