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
					add(new TestItem("congestedAdd0001", test.getGroupName(), "concurrent", congestedAddX(test, 1)));
					add(new TestItem("congestedAdd0002", test.getGroupName(), "concurrent", congestedAddX(test, 2)));
					add(new TestItem("congestedAdd0004", test.getGroupName(), "concurrent", congestedAddX(test, 4)));
					add(new TestItem("congestedAdd0008", test.getGroupName(), "concurrent", congestedAddX(test, 8)));
					add(new TestItem("congestedAdd0100", test.getGroupName(), "concurrent", congestedAddX(test, 100)));
					add(new TestItem("congestedAdd1000", test.getGroupName(), "concurrent", congestedAddX(test, 1000)));
					add(new TestItem("readWrite1", test.getGroupName(), "concurrent", readWrite1(test)));
					add(new TestItem("readWrite2", test.getGroupName(), "concurrent", readWrite2(test)));
					add(new TestItem("readWrite3", test.getGroupName(), "concurrent", readWrite3(test)));
					add(new TestItem("slowCompute", test.getGroupName(), "concurrent", slowCompute(test)));
					
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
	
	private Test congestedAddX(final CounterTest test, final int threadCount) {
		return new Test() {
			
			public void prepare(final int testSize) {

			}

			public void run(final int testSize) {
				ParallelRunner.run(threadCount, new Runnable() {
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
	
	private Test slowCompute(final CounterTest test) {
		return new Test() {
			
			public void prepare(final int testSize) {

			}

			public void run(final int testSize) {
				ParallelRunner.run(2, new Runnable() {
					public void run() {
						for (int i=0; i < testSize; i++) {
							test.longCalculation(100000000);
						}
					}
				});		
			}
			
		};
	}
	
	public static void wasteTime(int wastedCycles) {
		double k = 0;
		for (int i=0; i < wastedCycles; i++) {
			k++;
		}
		k = k + 0;
	}
	
}
