package me.test.test.locks;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class CasLockTest2 implements LockTest {
	

	private final AtomicInteger lockHolder = 
								new AtomicInteger(0);
	
	private final Integer id;
	
	public CasLockTest2() {
		this.id = null;
	}
	
	private CasLockTest2(int id) {
		this.id = Integer.valueOf(id);
	}

	public String getGroupName() {
		return "CasLock2";
	}

	public LockTest createInstance(int id) {
		return new CasLockTest2(id);
	}

	public boolean tryCommit(Iterator<LockTest> iterator) {
		
		while (!lockHolder.compareAndSet(0, this.id)) {
		
		}
		
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
			while (!lockHolder.compareAndSet(this.id, 0)) {

			}
		}
		
		
	}
	
	public void lockAndRelease() {
		
		while (!lockHolder.compareAndSet(0, 1)) {
			
		}
		
		try {
			@SuppressWarnings("unused")
			int i=0;
		}
		finally {
			while (!lockHolder.compareAndSet(1, 0)) {

			}
		}
	}
	
	public void lock() {
		
		while (!lockHolder.compareAndSet(0, 1)) {
			
		}
		
	}
	
	public void release() {
		
	
		while (!lockHolder.compareAndSet(1, 0)) {

		}
		
	}	
	

	public String toString() {
		return Integer.toString(id);
	}
	
}
