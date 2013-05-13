package me.test.test.concurrency.collectiontest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import me.test.test.Test;
import me.test.test.TestContainer;
import me.test.test.TestItem;
import me.test.util.concurrent.ParallelRunner;
import me.test.util.debug.StateDebuger;

public final class ConcurrentCollectionTester {
	
	public Set<TestContainer> getTests(final Class<? extends ConcurrentCollectionTest> testClass) {
		try {
			return Collections.unmodifiableSet(new HashSet<TestContainer>() {
				private static final long serialVersionUID = 1L;
				{
					ConcurrentCollectionTest test = testClass.newInstance();
					
					add(new TestItem("parallelFill", test.getGroupName(), "concurrentcollection", parallelFill(test, 2)));
					add(new TestItem("parallelFill01", test.getGroupName(), "concurrentcollection", parallelFill(test, 1)));
					add(new TestItem("parallelFill08", test.getGroupName(), "concurrentcollection", parallelFill(test, 8)));
					add(new TestItem("parallelFill100", test.getGroupName(), "concurrentcollection", parallelFill(test, 100)));
					add(new TestItem("listFillAndSum", test.getGroupName(), "concurrentcollection", listFillAndSum(test, 2)));
					add(new TestItem("listRead0001", test.getGroupName(), "concurrentcollection", listRead(test, 1)));
					add(new TestItem("listRead0002", test.getGroupName(), "concurrentcollection", listRead(test, 2)));
					add(new TestItem("listRead0006", test.getGroupName(), "concurrentcollection", listRead(test, 6)));
					add(new TestItem("listRead0008", test.getGroupName(), "concurrentcollection", listRead(test, 8)));
					add(new TestItem("listRead0032", test.getGroupName(), "concurrentcollection", listRead(test, 32)));
					add(new TestItem("listRead0100", test.getGroupName(), "concurrentcollection", listRead(test, 100)));
			}});
		} 
		catch (InstantiationException e) {
			throw new RuntimeException(e);
		} 
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Test parallelFill(final ConcurrentCollectionTest list, final int threadCount) {
		
		return new Test() {
			
			public void prepare(final int testSize) {
				list.prepareTest(1);
			}

			public void run(final int testSize) {
				ParallelRunner.run(threadCount, new Runnable() {
					public void run() {
						for (int i=0; i < testSize; i++) {
							
							list.addItem();
						}
						
					}
				});		
			}
			
		};
		
	}
	
	private Test listFillAndSum(final ConcurrentCollectionTest list, final int threadCount) {
		
		return new Test() {
			
			public void prepare(final int testSize) {
				//StateDebuger.setDebugLevel(true);
				list.prepareTest(1);
			}

			public void run(final int testSize) {
				ParallelRunner.run(threadCount, 
					new Runnable() {
						public void run() {
							for (int i=0; i < testSize; i++) {
								
								list.addItem();

								list.getSum();
								
							}
							
						}
				});
					
			}
			
		};
		
	}
	
	private Test listRead(final ConcurrentCollectionTest list, final int threadCount) {
		
		return new Test() {
			
			public void prepare(final int testSize) {
				//StateDebuger.setDebugLevel(true);
				list.prepareTest(testSize);
			}

			public void run(final int testSize) {
				ParallelRunner.run(threadCount, 
					new Runnable() {
						public void run() {
							for (int i=0; i < testSize; i++) {
								
								list.getSum();
								
							}
							
						}
				});
					
			}
			
		};
		
	}

	
}
