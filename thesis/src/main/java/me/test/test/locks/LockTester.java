package me.test.test.locks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import me.test.test.Test;
import me.test.test.TestContainer;
import me.test.test.TestItem;
import me.test.util.concurrent.ParallelRunner;

public class LockTester {

	public Set<TestContainer> getTests(final Class<? extends LockTest> testClass ) {
		try {
			return Collections.unmodifiableSet(new HashSet<TestContainer>() {
				
				private static final long serialVersionUID = 1L;

				{
					LockTest test = testClass.newInstance();
					
					add(new TestItem("cascadeLock", test.getGroupName(), "locks", cascadeLock(test)));
					add(new TestItem("parallelCascadeLock", test.getGroupName(), "locks", parallelCascadeLock(test)));
					
					add(new TestItem("lockandRelease", test.getGroupName(), "locks", lockandRelease(test)));
					add(new TestItem("parallelLockandRelease", test.getGroupName(), "locks", parallelLockandRelease(test)));
					
					add(new TestItem("multiLock", test.getGroupName(), "locks", multiLock(test)));
					add(new TestItem("parallelMultiLock", test.getGroupName(), "locks", parallelMultiLock(test)));
					
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
	
	private Test cascadeLock(final LockTest test) {
		
		return new Test() {
			List<LockTest> locks;

			public void prepare(int testSize) {
				locks = new ArrayList<LockTest>();
				for (int i = 1; i < testSize + 1; i++) {
					locks.add(test.createInstance(i));
				}
			}

			public void run(int testSize) {
				
				Iterator<LockTest> iterator = locks.iterator();
				
				if (iterator.hasNext()) {
					iterator.next().tryCommit(iterator);
				}
			}
		};
	}
	
	private Test parallelCascadeLock(final LockTest test)  {
		return new Test() {
			List<LockTest> locks;

			public void prepare(int testSize) {
				locks = new ArrayList<LockTest>();
				for (int i = 1; i < testSize + 1; i++) {
					locks.add(test.createInstance(i));
				}
			}

			public void run(int testSize) {
				
				ParallelRunner.run(2, new Runnable() {
					
					public void run() {
						Iterator<LockTest> iterator = locks.iterator();
						
						if (iterator.hasNext()) {
							iterator.next().tryCommit(iterator);
						}
					}
				});
				
			}
		};
	}
	
	private static Test lockandRelease(final LockTest test) {
		return new Test() {

			public void prepare(int testSize) {

			}

			public void run(final int testSize) {
				
				for (int i=0; i < testSize; i++) {
					test.lockAndRelease();
				}
			}
		};
	}

	private static Test parallelLockandRelease(final LockTest test) {
		return new Test() {

			public void prepare(int testSize) {
				
			}

			public void run(final int testSize) {
				
				ParallelRunner.run(2, new Runnable() {
					
					public void run() {
						for (int i=0; i < testSize; i++) {
							test.lockAndRelease();
						}
					}
				});
				
			}
		};
	}
	
	private static Test multiLock(final LockTest test) {
		return new Test() {

			List<LockTest> locks;

			public void prepare(int testSize) {
				locks = new ArrayList<LockTest>();
				for (int i = 1; i < testSize + 1; i++) {
					locks.add(test.createInstance(i));
				}
			}

			public void run(final int testSize) {
				
				for (LockTest lock : locks) {
					lock.lock();
				}
				
				for (LockTest lock : locks) {
					lock.release();
				}
			}
		};
	}
	
	private static Test parallelMultiLock(final LockTest test) {
		return new Test() {

			List<LockTest> locks;

			public void prepare(int testSize) {
				locks = new ArrayList<LockTest>();
				for (int i = 1; i < testSize + 1; i++) {
					locks.add(test.createInstance(i));
				}
			}

			public void run(final int testSize) {
				
				ParallelRunner.run(8, new Runnable() {
					
					public void run() {
						for (LockTest lock : locks) {
							lock.lock();
						}
						
						for (LockTest lock : locks) {
							lock.release();
						}
					}
				});
				
			}
		};
	}
}
