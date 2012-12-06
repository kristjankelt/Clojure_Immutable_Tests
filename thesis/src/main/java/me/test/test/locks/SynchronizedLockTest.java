package me.test.test.locks;

import java.util.Iterator;

public class SynchronizedLockTest implements LockTest {
	
	private final Integer id;
	
	public SynchronizedLockTest() {
		this.id = null;
	}
	
	private SynchronizedLockTest(int id) {
		this.id = Integer.valueOf(id);
	}

	public String getGroupName() {
		return "SynchronizedLock";
	}

	public LockTest createInstance(int id) {
		return new SynchronizedLockTest(id);
	}

	public synchronized boolean tryCommit(Iterator<LockTest> iterator) {
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
	
	public synchronized void lockAndRelease() {
		@SuppressWarnings("unused")
		int i=0;
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
