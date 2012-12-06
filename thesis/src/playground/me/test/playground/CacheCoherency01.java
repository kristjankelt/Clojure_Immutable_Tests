package me.test.playground;

import me.test.transactions.IdRunnable;
import me.test.util.concurrent.ParallelRunner;
import me.test.util.debug.CountTime;

public class CacheCoherency01 {
	
	private static int TEST_SIZE = 1000000000;
	
	private static int THREADS = 2;

	public static void main(String[] args) {
		
		CountTime.run(new Runnable() {
			public void run() {
				
				final int[] counters = new int[THREADS]; 
				
				ParallelRunner.run(THREADS, new IdRunnable() {
					
					public void run(int id) {
						@SuppressWarnings("unused")
						int fakeCalc = (id * (128 / (Integer.SIZE/8)) );
						
						for (long i=0; i < TEST_SIZE; i++) {
							counters[id]++;
						}
						
					} 
				});

				long totalValue = 0L;
				
				for (int value : counters) {
					totalValue += value;
				}
				
				System.out.println(totalValue);
			}
		});
	}
	
}
