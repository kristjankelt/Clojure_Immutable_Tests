package me.test.test.concurrency.collectiontest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import me.test.test.Test;
import me.test.test.TestContainer;
import me.test.test.TestItem;
import me.test.util.concurrent.ParallelRunner;

public final class ConcurrentCollectionTester {
	
	public Set<TestContainer> getTests(final Class<? extends ConcurrentCollectionTest> testClass) {
		try {
			return Collections.unmodifiableSet(new HashSet<TestContainer>() {
				private static final long serialVersionUID = 1L;
				{
					ConcurrentCollectionTest test = testClass.newInstance();
					
					add(new TestItem("parallelFill", test.getGroupName(), "concurrentcollection", parallelFill(test)));
			}});
		} 
		catch (InstantiationException e) {
			throw new RuntimeException(e);
		} 
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Test parallelFill(final ConcurrentCollectionTest list) {
		
		return new Test() {
			
			public void prepare(final int testSize) {

			}

			public void run(final int testSize) {
				ParallelRunner.run(2, new Runnable() {
					public void run() {
						for (int i=0; i < testSize; i++) {
							
							list.addItem();
						}
						
					}
				});		
			}
			
		};
		
	}

	
}
