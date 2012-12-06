package me.test.test.concurrency.countertest;

import java.util.concurrent.Callable;

import me.test.transactions.OptimisticRef2;
import me.test.transactions.OptimisticTransactionManager2;

public class OptimisticCounterTest2 implements CounterTest {
	
	private final OptimisticRef2<Long> counter;
	private final OptimisticRef2<Long> counter2;
	
	public OptimisticCounterTest2() {
		
		counter = new OptimisticRef2<Long>(0L);
		counter2 = new OptimisticRef2<Long>(0L);
		
	}
	
	public String getGroupName() {
		return "Optimistic2";
	}
	
	public Long increment(final boolean useCounterCounter) {
		
		return OptimisticTransactionManager2.transaction(new Callable<Long>() {

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
		
		
		
		return OptimisticTransactionManager2.transaction(new Callable<Long>() {

			public Long call() {
				//return counter.deref().longValue() + counter2.deref().longValue();
				
				return 	(useCounterCounter ? counter2.deref().longValue() : 0L) 
						+ counter.deref().longValue();
			}
		});
	}
}