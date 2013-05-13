package me.test.test.concurrency.countertest;

import java.util.concurrent.atomic.AtomicLong;


public class AtomicCounterTest implements CounterTest  {

	private final AtomicLong counter;
	private final AtomicLong counter2;
	
	public AtomicCounterTest() {
		
		counter = new AtomicLong(0L);
		counter2 = new AtomicLong(0L);
	}
	
	public String getGroupName() {
		return "Atomic";
	}

	public Long increment(final boolean useCounterCounter) {
		
		long counter1value = 0;
		long counter2value = 0;
		
		if (useCounterCounter) {
			synchronized (this) {
				counter1value = counter.incrementAndGet();
				counter2value = counter2.incrementAndGet();
			}
			
		}
		else {
			counter1value = counter.incrementAndGet();
		}
	
		return counter1value + counter2value;
		
	}

	public Long getValue(final boolean useCounterCounter) {
		
		if (useCounterCounter) {
			return counter.longValue();
		}
		else {
			synchronized (this) {
				return counter.longValue() + counter2.longValue();
			}
		}
	}

	@Override
	public Long longCalculation(int wastedCycles) {
		
		long counter1value = 0;
		long counter2value = 0;
		
		synchronized (this) {
			counter1value = counter.incrementAndGet();
			counter2value = counter2.incrementAndGet();
			
			ConcurrentTester.wasteTime(wastedCycles);
			
		}
		
	
		return counter1value + counter2value;
		
	}
	
}
