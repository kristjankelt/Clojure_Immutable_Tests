package me.test.transactions;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;

public final class OptimisticRef2<E>  implements 
											Ref<E>, 
											Comparable<OptimisticRef2<E>> {
	
	private static final AtomicInteger uniqueId = new AtomicInteger(0);
     
    public String toString() {
    	 return "Ref <" + Integer.toString(id) + ">";
    }
	
    private Integer id = Integer.valueOf(uniqueId.getAndIncrement());
    
	private E value;
	
	//private final Lock commitLock = new ReentrantLock();
	
	// TODO: consider replacing with one state object (but test performance before)
	private final ThreadLocal<Object> isInTransaction = new ThreadLocal<Object>();
//	private final ThreadLocal<Object> isLocking = new ThreadLocal<Object>();
	private final ThreadLocal<E> savedTransactionValue = new ThreadLocal<E>();
	private final ThreadLocal<E> currentTransactionValue = new ThreadLocal<E>();
	
	public OptimisticRef2(final E initialValue) {
		this.value = initialValue;
	} 

	public E deref() {
		if (OptimisticTransactionManager2.isInTransaction()) {
			if (!isInTransaction()) {
				
//				debugState("deref() new Transaction");
				
				setupTransaction(true);
				
				currentTransactionValue.set(savedTransactionValue.get());
				
			}
			else {
//				debugState("deref() existing Transaction");
			}
		
			return currentTransactionValue.get();
		}
		else {
		
		
			if (!isInTransaction()) {
			
//				debugState("deref() not in transaction yet");
				
				return value;
				
			}
			else {
//				debugState("deref() return value");
				
				return currentTransactionValue.get();
			}
		}

	}

	private void setupTransaction(boolean register) {
		if (register) {
			OptimisticTransactionManager2.registerRef(this);
		}
		isInTransaction.set(Boolean.TRUE);
		savedTransactionValue.set(value);
	}

	public E updateValue(E newValue) {
		if (!OptimisticTransactionManager2.isInTransaction()) {
			throw new RuntimeException("Ref accessed outside of the transaction.");
		}
		
		if (!isInTransaction()) {
	
//			debugState("updateValue(" + newValue + ") new Transaction");
			
			setupTransaction(true);
			
			
		}
//		else {
//			
//			debugState("updateValue(" + newValue + ") existing Transaction");
//		}
		
//		if (stateChanged()) {
//			throw new DataChangedException();
//		}
		
		currentTransactionValue.set(newValue);
		
		return newValue;
	}

	
	
	protected void reset() {
		if (!isInTransaction()) {
			throw new IllegalStateException("");
		}
		
//		debugState("reset() start");		
		
		//clear();
		savedTransactionValue.set(null);
		currentTransactionValue.set(null);
		isInTransaction.set(null);
		
//		debugState("reset() end");
		
//		if (isLocking.get() != null) {
//			
//			isLocking.set(null);
//			
//			commitLock.unlock();
//		
//		}
	}
	
	protected void finish() {
		
		if (!isInTransaction()) {
			throw new IllegalStateException("");
		}
		
//		debugState("finish() start");
		
//		value = currentTransactionValue.get();
		
		clear();
		
//		debugState("finish() end");
		
//		if (isLocking.get() != null) {
//			
//			isLocking.set(null);
//			
//			commitLock.unlock();
//		
//		}
	}
	
//	private static CasLock NULL_LOCK;
//	
//	{
//		if (NULL_LOCK == null) {
//			synchronized (CasLock.class) {
//				if (NULL_LOCK == null) {
//					NULL_LOCK = new CasLock(0);
//				}
//			}
//		}
//	}
//	
//	private static class CasLock {
//		private final int id;
//		
//		public CasLock(int id) {
//			this.id = id;
//		}
//		public String toString() {
//			return Integer.toString(id);
//		}
//	}
//
//	
//	private final AtomicReference<CasLock> lockHolder = 
//								new AtomicReference<CasLock>(NULL_LOCK);
	
	protected synchronized boolean tryCommit(Iterator<OptimisticRef2<?>> iterator) {
	
		if (!isInTransaction()) {
			throw new IllegalStateException("");
		}
		
//		debugState("tryCommit() start");
		
		
		
		if (stateChanged()) {
//			debugState("tryCommit() state changed");
			return false;
		}
		else {
			
			if (iterator.hasNext()) {
//				debugState("tryCommit() try next");
				
				if (iterator.next().tryCommit(iterator)) {
					
					value = currentTransactionValue.get();
					
//					debugState("tryCommit() save value = " + currentTransactionValue.get());
					
					return true;
				}
				else {
//					debugState("tryCommit() rollback");

					return false;
				}
			}
			else {
				value = currentTransactionValue.get();
				
//				debugState("tryCommit() save value2 = " + currentTransactionValue.get());
			
				return true;
			}
		}
		
	}
	
//	protected boolean commit() {
//		
//		if (!isInTransaction()) {
//			throw new IllegalStateException("");
//		}
//		
//		commitLock.lock();
//		
//		isLocking.set(Boolean.TRUE);
//		
////		debugState("commit() start");
//		
//		
//		if (stateChanged()) {
//			return false;
//		}
//		
////		debugState("commit() end");
//		
//		
//		return true;
//	}

	protected void clear() {
//		debugState("clear() start");
		
		
		savedTransactionValue.set(null);
		currentTransactionValue.set(null);
		isInTransaction.set(null);
		
//		debugState("clear() end");
		
	}
	
	private boolean stateChanged() {
		
		if (!isInTransaction()) {
			throw new IllegalStateException("");
		}
		
		// optimization
		E savedValue = savedTransactionValue.get();
		
		// optimization
		if (savedValue == value) {
			return false;
		}
		
		if (value == null && savedValue == null) {
//			debugState("stateChanged() is false: null == null");
			
			return false;
		}
		
		if (value != null && savedValue == null) {
			
//			debugState("stateChanged() is true: !null != null");
			
			return true;
		}
		
		if (value == null && savedValue != null) {
			
//			debugState("stateChanged() is true: null != !null");
			
			return true;
		}
		
		// BECAUSE VALUES SHOULD BE IMMUTABLE, SIMPLE REFERENCE TEST IS ENOUGH 	
		//if (!value.equals(savedTransactionValue.get())) {
		if (value != savedValue) {
			
//			debugState("stateChanged() is true: !null != !null");
			
			return true;
		}
		
//		debugState("stateChanged() is false: !null == !null");
		
		// see optimization above
		return false;
	}
	
	private boolean isInTransaction() {

		if (isInTransaction.get() == null) {
			return false;
		} 
		
		return true;
	}
	
	@SuppressWarnings("unused")
	private void debugState(String label) {
//		StateDebuger.debug(
//				label, 35, 10, 
//				this, 
//				isInTransaction.get(), 
//				value, 
//				savedTransactionValue.get(), 
//				currentTransactionValue.get());
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object compareTo) {
//		if (compareTo == null) {
//			throw new NullPointerException();
//		}
		
		if (this == compareTo) {
			return true;
		}
		else {
			return false;
		}
		
//		if (!(compareTo instanceof OptimisticRef2)) {
//			return false;
//		}
//		
//		return id.equals(((OptimisticRef<?>)compareTo).id);
	}


	public int compareTo(OptimisticRef2<E> compareTo) {
		if (compareTo == null) {
			throw new NullPointerException();
		}
		
		// this is optimization
		if (compareTo == this) {
			return 0;
		}
		
		return id.compareTo(compareTo.id);
	}

}
