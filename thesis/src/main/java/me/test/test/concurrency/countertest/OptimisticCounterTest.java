package me.test.test.concurrency.countertest;

import java.util.concurrent.Callable;

import me.test.transactions.OptimisticRef;
import me.test.transactions.OptimisticTransactionManager;

public class OptimisticCounterTest implements CounterTest {
	
	private final OptimisticRef<Long> counter;
	private final OptimisticRef<Long> counter2;
	
	public OptimisticCounterTest() {
		
		counter = new OptimisticRef<Long>(0L);
		counter2 = new OptimisticRef<Long>(0L);
		
	}
	
	public String getGroupName() {
		return "Optimistic";
	}
	
	public Long increment(final boolean useCounterCounter) {
		
		return OptimisticTransactionManager.transaction(new Callable<Long>() {

			public Long call() {
				
				long counter1value = 
						counter.updateValue(counter.deref().longValue() + 1).longValue();
				
				long counter2value = useCounterCounter ? 
						counter2.updateValue(counter2.deref().longValue() - 1).longValue() : 0;
				
				return counter1value + counter2value;		
					
			}
			
		});
	}
	
	public Long getValue(final boolean useCounterCounter) {
		
		return OptimisticTransactionManager.transaction(new Callable<Long>() {

			public Long call() {
				return counter.deref().longValue() + 
						(useCounterCounter ? counter2.deref().longValue() : 0L);
				
			}
		});
	}
}