package me.test.playground;

import me.test.transactions.IdRunnable;
import me.test.util.concurrent.ParallelRunner;
import me.test.util.debug.CountTime;

public class CacheCoherency02 {
	
	private static int TEST_SIZE = 1000000000;
	
	private static int THREADS = 4;

	public static void main(String[] args) {
		
		CountTime.run(new Runnable() {
			public void run() {
				
				final int[] counters = new int[(THREADS * (128 / (Integer.SIZE/8)) )]; 
				
				ParallelRunner.run(THREADS, new IdRunnable() {
					
					public void run(int id) {
						
						for (long i=0; i < TEST_SIZE; i++) {

							counters[(id * (128 / (Integer.SIZE/8)))]++;
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
