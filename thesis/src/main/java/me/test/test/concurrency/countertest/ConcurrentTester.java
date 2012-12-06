package me.test.test.concurrency.countertest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import me.test.test.Test;
import me.test.test.TestContainer;
import me.test.test.TestItem;
import me.test.util.concurrent.ParallelRunner;
//import me.test.util.debug.StateDebuger;

public class ConcurrentTester {
	
	public Set<TestContainer> getTests(final Class<? extends CounterTest> testClass ) {
		try {
			return Collections.unmodifiableSet(new HashSet<TestContainer>() {
				
				private static final long serialVersionUID = 1L;

				{
					CounterTest test = testClass.newInstance();
					
					add(new TestItem("congestedAdd1", test.getGroupName(), "concurrent", congestedAdd1(test)));
					add(new TestItem("congestedAdd2", test.getGroupName(), "concurrent", congestedAdd2(test)));
					add(new TestItem("readWrite1", test.getGroupName(), "concurrent", readWrite1(test)));
					add(new TestItem("readWrite2", test.getGroupName(), "concurrent", readWrite2(test)));
					add(new TestItem("readWrite3", test.getGroupName(), "concurrent", readWrite3(test)));
					
				}
			});
		
		} 
		catch (InstantiationException e) {
			throw new RuntimeException(e);
		} 
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Test congestedAdd1(final CounterTest test) {
		return new Test() {
			
			public void prepare(final int testSize) {

			}

			public void run(final int testSize) {
				ParallelRunner.run(2, new Runnable() {
					public void run() {
						for (int i=0; i < testSize; i++) {
							test.increment(false);
						}
					}
				});		
			}
			
		};
	}
	
	private Test congestedAdd2(final CounterTest test) {
		return new Test() {
			
			public void prepare(final int testSize) {

			}

			public void run(final int testSize) {
				ParallelRunner.run(2, new Runnable() {
					public void run() {
						for (int i=0; i < testSize; i++) {
							test.increment(true);
						}
					}
				});		
			}
			
		};
	}
	
	private Test readWrite1(final CounterTest test) {
		return new Test() {
			
			public void prepare(final int testSize) {

			}

			public void run(final int testSize) {
				ParallelRunner.run(
						new Runnable() {
							public void run() {
								for (int i=0; i < testSize; i++) {
									
									@SuppressWarnings("unused")
									Long value = test.getValue(false);
								}
							}
						},			
						new Runnable() {
							public void run() {
								for (int i=0; i < testSize; i++) {

									@SuppressWarnings("unused")
									Long value = test.increment(false);
								}
							}
						}
					);
			}
			
		};
	}
	
	private Test readWrite2(final CounterTest test) {
		return new Test() {
			
			public void prepare(final int testSize) {

			}

			public void run(final int testSize) {
				ParallelRunner.run(
						new Runnable() {
							public void run() {
								for (int i=0; i < testSize; i++) {
									
									@SuppressWarnings("unused")
									Long value = test.getValue(true);
								}
							}
						},			
						new Runnable() {
							public void run() {
								for (int i=0; i < testSize; i++) {

									@SuppressWarnings("unused")
									Long value = test.increment(true);
								}
							}
						}
					);
			}
			
		};
	}
	
	private Test readWrite3(final CounterTest test) {
		return new Test() {
			
			public void prepare(final int testSize) {

			}

			public void run(final int testSize) {
				
				
				ParallelRunner.run(
						new Runnable() {
							public void run() {
								for (int i=0; i < testSize; i++) {

									if (test.getValue(true).longValue() != 0L) {
										throw new IllegalStateException();
									}
								}
							}
						},		
						new Runnable() {
							public void run() {
								for (int i=0; i < testSize; i++) {
									@SuppressWarnings("unused")
									Long value = test.increment(true);
								}
							}
						},		
						new Runnable() {
							public void run() {
								for (int i=0; i < testSize; i++) {
									
									if (test.getValue(true).longValue() != 0L) {
										throw new IllegalStateException();
									}
								}
							}
						},		
						new Runnable() {
							public void run() {
								for (int i=0; i < testSize; i++) {

									@SuppressWarnings("unused")
									Long value = test.increment(true);
								}
							}
						}
					);
			}
			
		};
	}
	
}
