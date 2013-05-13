package me.test.test.concurrency.countertest;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class ReadWriteLockCounterTest implements CounterTest  {

	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private Long counter;
	private Long counter2;
	
	public ReadWriteLockCounterTest() {
		
		counter = Long.valueOf(0L);
		counter2 = Long.valueOf(0L);
		
	}
	
	public String getGroupName() {
		return "ReadWriteLock";
	}

	public Long increment(final boolean useCounterCounter) {
		
		lock.writeLock().lock();
		
		try {
			long counter1value = 0L;
			long counter2value = 0L;
			
			counter1value = counter.longValue() + 1L;
			counter2value = useCounterCounter ? counter2.longValue() - 1L : 0L;
			
			counter = Long.valueOf(counter1value);
			counter2 = Long.valueOf(counter2value);
		
			return counter1value + counter2value;
		}
		finally {
			lock.writeLock().unlock();
		}
		
	}

	public Long getValue(final boolean useCounterCounter) {
		
		lock.readLock().lock();
		
		try {
			return counter.longValue() + 
						(useCounterCounter ? counter2.longValue() : 0L);
		}
		finally {
			lock.readLock().unlock();
		}
		
	}

	@Override
	public Long longCalculation(final int wastedCycles) {
		
		lock.writeLock().lock();
		
		try {
			long counter1value = 0L;
			long counter2value = 0L;
			
			counter1value = counter.longValue() + 1L;
			counter2value = counter2.longValue() - 1L;
			
			counter = Long.valueOf(counter1value);
			counter2 = Long.valueOf(counter2value);
			
			ConcurrentTester.wasteTime(wastedCycles);
			
		
			return counter1value + counter2value;
		}
		finally {
			lock.writeLock().unlock();
		}
	}
	
}
