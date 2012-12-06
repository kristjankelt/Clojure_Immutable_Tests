package me.test.playground;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import me.test.transactions.IdRunnable;
import me.test.util.concurrent.ParallelRunner;
import me.test.util.debug.CountTime;

@SuppressWarnings("unused")
public class ConcurrentCounter {
	
	private static int TEST_SIZE = 100000;
	
	private static int THREADS = 4;
			
	private static class Counter {
		private volatile int value = 0;
		private volatile boolean repeat;
	}
	
	private BigInteger bufferedTotalCount = BigInteger.ZERO;

	private ConcurrentMap<Long, Counter> counters = new ConcurrentHashMap<Long, ConcurrentCounter.Counter>();
	
	private ConcurrentMap<Long, Integer> counters2 = new ConcurrentHashMap<Long, Integer>();
	
	private ThreadLocal<Counter> value = new ThreadLocal<Counter>() {
		protected Counter initialValue() {
			long counterId = Thread.currentThread().getId(); 
			
			Counter counter = counters.get(counterId);
			
			if (counter == null) {
				
				counter = new Counter();
				
				Counter previous = counters.putIfAbsent(counterId, counter);
				
				if (previous != null) {
					counter = previous;
				}
			}
			
			return counter;
		};
	};
	
	private void increment() {
		
		long counterId = Thread.currentThread().getId(); 
		
		Counter counter = counters.get(counterId);
		
		if (counter == null) {
			
			counter = new Counter();
			
			Counter previous = counters.putIfAbsent(counterId, counter);
			
			if (previous != null) {
				counter = previous;
			}
		}
		
		counter.value++;
		
		if (counter.value == Integer.MAX_VALUE - 1) {
			incrementBufferedTotalCount(counter.value);
			counter.value = 0;
		}
		
//		Counter counter2 = counters.get(counterId);
//		
//		if (counter2 == null || counter != counter2) {
//			increment();
//		}
		
	}

	private synchronized void incrementBufferedTotalCount(int value) {
		bufferedTotalCount = bufferedTotalCount.add(BigInteger.valueOf(value)); 
	}

	private synchronized BigInteger getBufferedTotalCount() {
		return bufferedTotalCount; 
	}

	private void increment2() {
		
		value.get().value++;
		
		if (value.get().value == Integer.MAX_VALUE) {
			incrementBufferedTotalCount(value.get().value);
			value.get().value = 0;
		}
		
	}
	

	private void increment3() {
		
		long counterId = Thread.currentThread().getId();
		
		Integer value = counters2.get(counterId);
		
		if (value == null) {
			
			value = Integer.valueOf(0);
			
			Integer previous = counters2.putIfAbsent(counterId, value);
			
			if (previous != null) {
				value = previous;
			}
		}
		
		value = Integer.valueOf(value.intValue() + 1);		
		
		counters2.put(counterId, value);
		
	}
	
	private BigInteger total() {
		
		long total = 0;
		
		for (Counter counter : counters.values()) {
			total += counter.value;
		}
		
		return getBufferedTotalCount().add(BigInteger.valueOf(total));
	}
	
	private static void calculate() {
		for (int i=1; i < 3; i++) {
			double result = Math.sqrt(Math.pow(10, i));
			
		}
	}
	
	public static void main(String[] args) {
		
		// FASTEST OF COURSE
		CountTime.run(new Runnable() {
			public void run() {
				
				final long[] counterValue = new long[] {0}; 
				
				ParallelRunner.run(1, new Runnable() {
					
					public void run() {
						long counter = 0;
						
						for (long i=0; i < TEST_SIZE * THREADS; i++) {
							calculate();
							counter++;
						}
						
						counterValue[0] = counter;
					}
				});
				
				System.out.println(counterValue[0]);
			}
		});
		
		
		// CURIOUS?
		CountTime.run(new Runnable() {
			public void run() {
				
				final int[] counters = new int[THREADS]; 
				
				ParallelRunner.run(THREADS, new IdRunnable() {
					
					public void run(int id) {
						
						for (long i=0; i < TEST_SIZE; i++) {
							calculate();
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
		
		// CURIOUS?
		CountTime.run(new Runnable() {
			public void run() {
				
				final int[] counters = new int[(THREADS * (128 / (Integer.SIZE/8)) )]; 
				
				ParallelRunner.run(THREADS, new IdRunnable() {
					
					public void run(int id) {
						long counter = 0;
						
						for (long i=0; i < TEST_SIZE; i++) {
							calculate();
							counters[(id * (128 / (Integer.SIZE/8)) )]++;
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
		
		CountTime.run(new Runnable() {
			public void run() {
				
				//final long[] counterValue = new long[] {0}; 
				
				ParallelRunner.run(THREADS, new Runnable() {
					
					public void run() {
						long counter = 0;
						
						for (long i=0; i < TEST_SIZE * THREADS; i++) {
							calculate();
							counter++;
						}
						
						//counterValue[0] = counter;
					} 
				});
				
				System.out.println("-");
			}
		});
		
		final ConcurrentCounter counter = new ConcurrentCounter(); 
		
		CountTime.run(new Runnable() {
			public void run() {
				ParallelRunner.run(THREADS, new Runnable() {
		
					public void run() {
						
						for (int i=0; i < TEST_SIZE; i++) {
							calculate();
							counter.increment();
						}
							
					}
			
				});
				
				System.out.println(counter.total());
	
			}
		});
		
		
		final ConcurrentCounter counter2 = new ConcurrentCounter(); 
		
		// A REFERENCE
		CountTime.run(new Runnable() {
			public void run() {
				ParallelRunner.run(THREADS, new Runnable() {
		
					public void run() {
						
						for (int i=0; i < TEST_SIZE; i++) {
							calculate();
							counter2.increment2();
						}
							
					}
			
				});
				
				System.out.println(counter2.total());
	
			}
		});
		
		// THIS IS THE SLOWEST
//		CountTime.run(new Runnable() {
//			public void run() {
//				ParallelRunner.run(THREADS, new Runnable() {
//		
//					public void run() {
//						
//						for (int i=0; i < TEST_SIZE; i++) {
//							calculate();
//							counter.increment3();
//						}
//							
//					}
//			
//				});
//				
//				System.out.println("-");
//	
//			}
//		});
		
		
		// THIS IS SLOW TOO
		final AtomicLong counter3 = new AtomicLong(0);
		
		CountTime.run(new Runnable() {
			public void run() {
				ParallelRunner.run(THREADS, new Runnable() {
		
					public void run() {
						
						for (int i=0; i < TEST_SIZE; i++) {
							calculate();
							counter3.incrementAndGet();
						}
							
					}
			
				});
				
				System.out.println(counter3.get());
	
			}
		});
		
		// JUST TESTING
		CountTime.run(new Runnable() {
			public void run() {
				ParallelRunner.run(THREADS, new Runnable() {
		
					public void run() {
						
						AtomicInteger counter = new AtomicInteger(0);
						
						for (int i=0; i < TEST_SIZE; i++) {
							calculate();
							counter.incrementAndGet();
						}
							
					}
			
				});
				
				System.out.println("-");
	
			}
		});
		
		
		
		
	}
}
