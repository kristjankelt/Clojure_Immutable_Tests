package me.test.test.concurrency.countertest;

import java.util.concurrent.Callable;

import me.test.transactions.ClojureRef;

import clojure.lang.LockingTransaction;

public class ClojureSTMCounterTest implements CounterTest  {

	private final ClojureRef<Long> counter;
	private final ClojureRef<Long> counter2;
	

	public ClojureSTMCounterTest() {
		
		counter = new ClojureRef<Long>(0L);
		counter2 = new ClojureRef<Long>(0L);
		
	}
	
	public String getGroupName() {
		return "ClojureSTM";
	}
	
	public Long increment(final boolean useCounterCounter) {
		
		try {
			return (Long) LockingTransaction.runInTransaction(new Callable<Long>() {

				public Long call() {

					long counter1value = 
							counter.updateValue(counter.deref().longValue() + 1).longValue();
					
					long counter2value = useCounterCounter ? 
							counter2.updateValue(counter2.deref().longValue() - 1).longValue() : 0;
					
					
					return counter1value + counter2value;		
							
				}
				
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	public Long getValue(final boolean useCounterCounter) {
		try {
			return (Long) LockingTransaction.runInTransaction(new Callable<Long>() {

				public Long call() {
					return counter.deref().longValue() + 
							(useCounterCounter ? counter2.deref().longValue() : 0L);	
				}	
			});
			
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Long longCalculation(final int wastedCycles) {
		
		try {
			return (Long) LockingTransaction.runInTransaction(new Callable<Long>() {

				public Long call() {

					long counter1value = 
							counter.updateValue(counter.deref().longValue() + 1).longValue();
					
					long counter2value =  
							counter2.updateValue(counter2.deref().longValue() - 1).longValue();
					
					ConcurrentTester.wasteTime(wastedCycles);
					
					return counter1value + counter2value;		
							
				}
				
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
		
	
}
