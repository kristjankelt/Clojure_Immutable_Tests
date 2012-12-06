package me.test.test.concurrency.countertest;

import java.util.concurrent.Callable;

import me.test.transactions.BlockingRef;
import me.test.transactions.BlockingTransactionManager;

public class BlockingCounterTest implements CounterTest  {

	private final BlockingRef<Long> counter;
	private final BlockingRef<Long> counter2;
	
	
	public BlockingCounterTest() {
		
		counter = new BlockingRef<Long>(0L);
		counter2 = new BlockingRef<Long>(0L);
		
	}
	
	public String getGroupName() {
		return "Blocking";
	}

	public Long increment(final boolean useCounterCounter) {
		
		return BlockingTransactionManager.transaction(new Callable<Long>() {

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
		
		
		return BlockingTransactionManager.transaction(new Callable<Long>() {

			public Long call() {
				return counter.deref().longValue() + 
						(useCounterCounter ? counter2.deref().longValue() : 0L);
				
			}
		});
	}
	
}
