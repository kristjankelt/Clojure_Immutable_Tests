package me.test.playground;

import me.test.util.concurrent.ParallelRunner;
import me.test.util.debug.CountTime;

public class VolatileBooleanTest {
	
	private static final int TEST_SIZE = 1000000;
	
	private volatile Boolean flag;
	
	VolatileBooleanTest() {
		flag = Boolean.FALSE;
	}
	
	private void setAndGetFlag() {
		
		boolean finalFlagValue = setAndGet();
		
		if (finalFlagValue) {
			int dummy = 0;
			if (dummy == 0) {
				dummy = 1;
			}
		}
		
	}

	private boolean setAndGet() {
		boolean finalFlagValue = false;
		
		if (flag.booleanValue() == Boolean.TRUE) {
			finalFlagValue = true;
		}
		else {
			synchronized (flag) {
				if (flag.booleanValue() == Boolean.FALSE) {
					finalFlagValue = true;
				}
				else {
					finalFlagValue = false;
				}
				
				flag = Boolean.TRUE;
			}
		}
		return finalFlagValue;
	}


	public static void main(String[] args) throws InterruptedException {
		
		final VolatileBooleanTest test = new VolatileBooleanTest();

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
