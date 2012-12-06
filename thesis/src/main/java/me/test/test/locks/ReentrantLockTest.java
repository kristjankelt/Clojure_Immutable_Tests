package me.test.test.locks;

import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest implements LockTest {
	
	private final Integer id;
	private final Lock lock = new ReentrantLock();
	
	public ReentrantLockTest() {
		this.id = null;
	}
	
	private ReentrantLockTest(int id) {
		this.id = Integer.valueOf(id);
	}

	public String getGroupName() {
		return "ReentrantLock";
	}

	public LockTest createInstance(int id) {
		return new ReentrantLockTest(id);
	}

	public boolean tryCommit(Iterator<LockTest> iterator) {
		lock.lock();
		
		try {
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
		finally {
			lock.unlock();
		}
	}
	
	public void lockAndRelease() {
		lock.lock();
		
		try {
			@SuppressWarnings("unused")
			int i = 0;
		}
		finally {
			lock.unlock();
		}
	}

	
	public void lock() {
		lock.lock();
	}

	public void release() {
		lock.unlock();
	}

	public String toString() {
		return Integer.toString(id);
	}

	
	
}
