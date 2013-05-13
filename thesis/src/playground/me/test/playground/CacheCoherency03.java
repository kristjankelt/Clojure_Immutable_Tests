package me.test.playground;

import me.test.transactions.IdRunnable;
import me.test.util.concurrent.ParallelRunner;
import me.test.util.debug.CountTime;

public class CacheCoherency03 {
	
	private static int TEST_SIZE = 1000000000;
	
	private static int THREADS = 4;

	public static void main(String[] args) {
		
		// FOR REFERENCE
		CountTime.run(new Runnable() {
			
			public void run() {
				
				final int[] counters = new int[THREADS]; 
				
				ParallelRunner.run(1, new IdRunnable() {
					
					public void run(int id) {
						
						for (int t=0; t < THREADS; t++) {
							
							for (long i=0; i < TEST_SIZE; i++) {
	
								counters[t]++;
							}
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
