package me.test.transactions;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import me.test.util.debug.StateDebuger;

public class BlockingTransactionManager  {
	
	private final static ConcurrentMap<BlockingRef<?>,Lock> registeredLocks = new ConcurrentHashMap<BlockingRef<?>,Lock>();
	private final static ThreadLocal<Map<BlockingRef<?>, Locker>> currentLocks = new ThreadLocal<Map<BlockingRef<?>, Locker>>();
	
	private static final ThreadLocal<Object> isInTransaction = new ThreadLocal<Object>();

	private static class Locker {
		
		private final Lock lock;
		private int lockCount = 0;
		
		public Locker(Lock lock) {
			if (lock == null) {
				throw new NullPointerException();
			}
			this.lock = lock;
		}
		
		public void lock() {
			lock.lock();
			lockCount++;
		}
		public void release() {
			for (int i = 0; i < lockCount; i++) {
				lock.unlock();
			}
		}
		
		@Override
		public String toString() {
			return lock.toString();
		}
		
		@Override
		public boolean equals(Object obj) {

			if (!(obj instanceof Locker)) {
				return false;
			}
			
			return lock.equals(((Locker)obj).lock);
		}

		@Override
		public int hashCode() {
			return lock.hashCode();
		}
	}
	
	@SuppressWarnings("finally")
	public static <V> V transaction(Callable<V> transaction) {
		
		currentLocks.set(new TreeMap<BlockingRef<?>, Locker>());
		
		isInTransaction.set(Boolean.TRUE);
		
		
		StateDebuger.debug("BEFORE transaction.call");
		
		V returnValue = null;
		
		try {
			returnValue = transaction.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			
			clearLocks();
			
			isInTransaction.set(null);
			
			
			return returnValue;
		}
			
	}

	private static void clearLocks() {
		for (Locker locker : currentLocks.get().values()) {
			StateDebuger.debug("RELEASE LOCK " + locker);
			
			locker.release();
		}
		
		currentLocks.remove();
	}
	
	public static <E> void getReadLock(BlockingRef<E> ref) {
		getWriteLock(ref);
	}
	
	public static <E> void getWriteLock(BlockingRef<E> ref) {
		StateDebuger.debug("GET LOCK ");
		
		if (currentLocks.get() == null) {
			return;
		}
			
		if (!currentLocks.get().containsKey(ref)) {
		
			Lock currentLock = registerLock(ref);
				
			currentLocks.get().put(ref, new Locker(currentLock));
			
			currentLocks.get().get(ref).lock();
		}

	}
	
	private static <E> Lock registerLock(BlockingRef<E> ref) {
		Lock registeredLock = registeredLocks.get(ref);
		
	    if (registeredLock == null) {
	    	
	    	Lock newLock = new ReentrantLock();
	    
	    	registeredLock = registeredLocks.putIfAbsent(ref, newLock);
	        if (registeredLock == null) {
	        	registeredLock = newLock;
	        }
	    }
	    
	    return registeredLock;
	}
	
	public static boolean isInTransaction() {
		
		if ( isInTransaction.get() == null) {
			return false;
		} 
		
		return true;
	}
}
