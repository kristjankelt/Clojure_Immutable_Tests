package me.test.playground;

import java.util.concurrent.atomic.AtomicIntegerArray;

import me.test.util.debug.CountTime;

public class AtomicIntegerArrayTest {
	
	private static int TEST_SIZE = 10000;
	
	private void test(AtomicIntegerArray array, int i) {
		array.set(i, i);
	}

	public static void main(String[] args) {
		
		final AtomicIntegerArrayTest test = new AtomicIntegerArrayTest();
		final AtomicIntegerArray array = new AtomicIntegerArray(TEST_SIZE);
		
		CountTime.run(new Runnable() {

			public void run() {
				
				for (int i=0; i < TEST_SIZE; i++) {
					test.test(array, i);
				}
			}
			
		});
		
	}
}
