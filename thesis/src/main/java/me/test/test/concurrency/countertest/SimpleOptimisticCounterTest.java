package me.test.test.concurrency.countertest;

import me.test.transactions.Passable;
import me.test.transactions.SimpleRef;


public class SimpleOptimisticCounterTest implements CounterTest {

	private final SimpleRef<Long> counter;
	
	public SimpleOptimisticCounterTest() {
		
		counter = new SimpleRef<Long>(0L);
		
	}
	
	public String getGroupName() {
		return "SimpleOptimistic";
	}
	
	public Long increment(final boolean useCounterCounter) {
		
		return counter.updateValueOptimistically(new Passable<Long>() {
			
			public Long call(Long value) {
				return Long.valueOf(value.longValue() + 1);
			}
		});
	}

	public Long getValue(final boolean useCounterCounter) {
		return counter.deref();
	}

}
