package me.test.test.locks;

import java.util.Iterator;

public class SynchronizedThisLockTest implements LockTest {
	
	private final Integer id;
	
	public SynchronizedThisLockTest() {
		this.id = null;
	}
	
	private SynchronizedThisLockTest(int id) {
		this.id = Integer.valueOf(id);
	}

	public String getGroupName() {
		return "SynchronizedThisLock";
	}

	public LockTest createInstance(int id) {
		return new SynchronizedThisLockTest(id);
	}

	public boolean tryCommit(Iterator<LockTest> iterator) {
		synchronized (this) {
			if (iterator.hasNext()) {
						
				if (iterator.next().tryCommit(iterator)) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return true;
			}
		}
	}
	

	public void lockAndRelease() {
		synchronized (this) {
			@SuppressWarnings("unused")
			int i=0;
		}
	}
	
	public void lock() {
		throw new UnsupportedOperationException();
	}

	public void release() {
		throw new UnsupportedOperationException();
	}
	

	public String toString() {
		return Integer.toString(id);
	}

}
