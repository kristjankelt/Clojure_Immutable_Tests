package me.test.test.locks;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

public class CasLockTest implements LockTest {
	
	private static CasLock NULL_LOCK = new CasLock(0);
	
//	{
//		if (NULL_LOCK == null) {
//			synchronized (CasLock.class) {
//				if (NULL_LOCK == null) {
//					NULL_LOCK = new CasLock(0);
//				}
//			}
//		}
//	}
	
	private static class CasLock {
		private final int id;
		
		public CasLock(int id) {
			this.id = id;
		}
		public String toString() {
			return Integer.toString(id);
		}
	}

	
	private AtomicReference<CasLock> lockHolder = 
								new AtomicReference<CasLock>(NULL_LOCK);
	
	private final Integer id;
	
	public CasLockTest() {
		this.id = null;
	}
	
	private CasLockTest(int id) {
		this.id = Integer.valueOf(id);
	}

	public String getGroupName() {
		return "CasLock";
	}

	public LockTest createInstance(int id) {
		return new CasLockTest(id);
	}

	public boolean tryCommit(Iterator<LockTest> iterator) {
		
		CasLock current = new CasLock(this.id);
		
		while (!lockHolder.compareAndSet(NULL_LOCK, current)) {
		
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
			while (!lockHolder.compareAndSet(current, NULL_LOCK)) {

			}
		}
		
		
	}
	
	public void lockAndRelease() {
		CasLock current = new CasLock(Integer.valueOf(1));
		
		while (!lockHolder.compareAndSet(NULL_LOCK, current)) {
			
		}
		try {
			@SuppressWarnings("unused")
			int i=0; // DO almost NOTHING
		}
		finally {
			while (!lockHolder.compareAndSet(current, NULL_LOCK)) {

			}
		}
	}
	

	private ThreadLocal<CasLock> lockVar = new ThreadLocal<CasLock>();
	
	public void lock() {
		
		CasLock current = new CasLock(Integer.valueOf(1));
		
		lockVar.set(current);
		
		while (!lockHolder.compareAndSet(NULL_LOCK, current)) {
			
		}
		
	}
	
	public void release() {
		
		CasLock current = lockVar.get();
		
		if (current == null) {
			return;
		}
		
		while (!lockHolder.compareAndSet(current, NULL_LOCK)) {

		}
		
		lockVar.remove();
	}	
	

	public String toString() {
		return Integer.toString(id);
	}
	
	
}
