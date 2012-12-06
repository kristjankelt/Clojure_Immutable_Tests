package me.test.test.concurrency.countertest;

public interface CounterTest {
	
	public String getGroupName();

	public Long increment(final boolean useCounterCounter);

	public Long getValue(final boolean useCounterCounter);
	
}