package me.test.test.concurrency.countertest;

import java.util.concurrent.atomic.AtomicReference;


public class AtomicCounterTest2 implements CounterTest  {

	private final AtomicReference<long[]> counter;
	
	public AtomicCounterTest2() {
		
		counter = new AtomicReference<long[]>(new long[] {0,0});
	}
	
	public String getGroupName() {
		return "Atomic2";
	}

	public Long increment(final boolean useCounterCounter) {
		
		long[] currentValue = counter.get();
		
		while (!counter.compareAndSet(
						currentValue, 
						new long[] {
								currentValue[0] + 1,
								currentValue[1] - 1
						})) {
			
			currentValue = counter.get();
		}
	
		return currentValue[0] + currentValue[1];
		
	}

	public Long getValue(final boolean useCounterCounter) {
		
		if (useCounterCounter) {
			long[] value = counter.get();
			return value[0] + value[1];
		}
		else {
			return counter.get()[0];
		}
	}

	@Override
	public Long longCalculation(int wastedCycles) {
		
		long[] currentValue = counter.get();
		
		while (!counter.compareAndSet(
						currentValue, 
						new long[] {
								currentValue[0] + 1,
								currentValue[1] - 1
						})) {
			
			ConcurrentTester.wasteTime(wastedCycles);
			
			currentValue = counter.get();
		}
	
		return currentValue[0] + currentValue[1];
	}
	
}
