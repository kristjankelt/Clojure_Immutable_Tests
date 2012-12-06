package me.test.test.concurrency.countertest;


public class SynchronizedCounterTest2 implements CounterTest  {

	private Long counter;
	private Long counter2;
	
	public SynchronizedCounterTest2() {
		
		counter = Long.valueOf(0L);
		counter2 = Long.valueOf(0L);
		
	}
	
	public String getGroupName() {
		return "Synchronized2";
	}

	public synchronized Long increment(final boolean useCounterCounter) {
		
		long counter1value = 0L;
		long counter2value = 0L;
		
		counter1value = counter.longValue() + 1L;
		counter2value = useCounterCounter ? counter2.longValue() - 1L : 0L;
		
		counter = Long.valueOf(counter1value);
		counter2 = Long.valueOf(counter2value);
	
		return counter1value + counter2value;
		
	}

	public synchronized Long getValue(final boolean useCounterCounter) {
		
		return counter.longValue() + 
					(useCounterCounter ? counter2.longValue() : 0L);
		
	}
	
}
