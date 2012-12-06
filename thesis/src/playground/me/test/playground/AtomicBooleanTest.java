package me.test.playground;

import java.util.concurrent.atomic.AtomicBoolean;

import me.test.util.concurrent.ParallelRunner;
import me.test.util.debug.CountTime;

public class AtomicBooleanTest {
	
	private static final int TEST_SIZE = 1000000;
	
	private final AtomicBoolean flag;
	
	AtomicBooleanTest() {
		flag = new AtomicBoolean(false);
	}
	
	private void setAndGetFlag() {
		

		if (flag.getAndSet(true)) {
			int dummy = 0;
			if (dummy == 0) {
				dummy = 1;
			}
		}
		
	}


	public static void main(String[] args) throws InterruptedException {
		
		final AtomicBooleanTest test = new AtomicBooleanTest();

		CountTime.run(new Runnable() {
			
			public void run() {
				ParallelRunner.run(new Runnable() {

					public void run()  {
						for (int i=0; i < TEST_SIZE; i++) {
							test.setAndGetFlag();
						}
					}
					
				},new Runnable() {

					public void run() {
						for (int i=0; i < TEST_SIZE; i++) {
							test.setAndGetFlag();
						}
					}
					
				});
			}
		});
		
	}
}
