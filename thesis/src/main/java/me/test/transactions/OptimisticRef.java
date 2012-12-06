package me.test.transactions;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import me.test.util.debug.StateDebuger;

public final class OptimisticRef<E> implements Ref<E>, Comparable<OptimisticRef<E>> {
	
	private static final AtomicInteger uniqueId = new AtomicInteger(0);
     
    public String toString() {
    	 return "Ref <" + Integer.toString(id) + ">";
    }
	
    private Integer id = Integer.valueOf(uniqueId.getAndIncrement());
    
	private E value;
	
	private final Lock commitLock = new ReentrantLock();
	
	private final ThreadLocal<Object> isInTransaction = new ThreadLocal<Object>();
	private final ThreadLocal<Object> isLocking = new ThreadLocal<Object>();
	private final ThreadLocal<E> savedTransactionValue = new ThreadLocal<E>();
	private final ThreadLocal<E> currentTransactionValue = new ThreadLocal<E>();
	
	public OptimisticRef(final E initialValue) {
		this.value = initialValue;
	} 

	public E deref() {
		if (OptimisticTransactionManager.isInTransaction()) {
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
			OptimisticTransactionManager.registerRef(this);
		}
		isInTransaction.set(Boolean.TRUE);
		savedTransactionValue.set(value);
	}

	public E updateValue(E newValue) {
		if (!OptimisticTransactionManager.isInTransaction()) {
			throw new RuntimeException("Ref accessed outside of the transaction.");
		}
		
		if (!isInTransaction()) {
	
//			debugState("updateValue(" + newValue + ") new Transaction");
			
			setupTransaction(true);
			
			
		}
		else {
			
//			debugState("updateValue(" + newValue + ") existing Transaction");
		}
		
		if (stateChanged()) {
			//throw new DataChangedException();
		}
		
		currentTransactionValue.set(newValue);
		
		return newValue;
	}

	
	
	protected void reset() {
		if (!isInTransaction()) {
			throw new IllegalStateException("");
		}
		
//		debugState("reset() start");		
		
		clear();
		
//		debugState("reset() end");
		
		if (isLocking.get() != null) {
			
			isLocking.set(null);
			
			commitLock.unlock();
		
		}
	}
	
	protected void finish() {
		if (!isInTransaction()) {
			throw new IllegalStateException("");
		}
		
//		debugState("finish() start");
		
		value = currentTransactionValue.get();
		
		clear();
		
//		debugState("finish() end");
		
		if (isLocking.get() != null) {
			
			isLocking.set(null);
			
			commitLock.unlock();
		
		}
	}
	
	
	protected boolean commit() {
		
		if (!isInTransaction()) {
			throw new IllegalStateException("");
		}
		
		commitLock.lock();
		
		isLocking.set(Boolean.TRUE);
		
//		debugState("commit() start");
		
		
		if (stateChanged()) {
			return false;
		}
		
//		debugState("commit() end");
		
		
		return true;
	}

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
		StateDebuger.debug(
				label, 35, 10, 
				this, 
				isInTransaction.get(), 
				value, 
				savedTransactionValue.get(), 
				currentTransactionValue.get());
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
		
//		if (!(compareTo instanceof OptimisticRef)) {
//			return false;
//		}
//		
//		return id.equals(((OptimisticRef<?>)compareTo).id);
	}


	public int compareTo(OptimisticRef<E> compareTo) {
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
