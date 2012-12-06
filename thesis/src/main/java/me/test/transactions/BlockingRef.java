package me.test.transactions;

import java.util.concurrent.atomic.AtomicInteger;

import me.test.util.debug.StateDebuger;

public final class BlockingRef<E> implements Ref<E>, Comparable<BlockingRef<E>> {
	
	private static final AtomicInteger uniqueId = new AtomicInteger(0);
    
    public String toString() {
    	 return "Ref <" + Integer.toString(id) + ">";
    }
	
    private Integer id = Integer.valueOf(uniqueId.getAndIncrement());

	private E value;
	
	public BlockingRef(final E initialValue) {
		
		this.value = initialValue;
		
	}
		
	public E updateValue(final E newValue) {
		StateDebuger.debug("ACQUIRE WRITE LOCK");
		
		BlockingTransactionManager.getWriteLock(this);
		
		this.value = newValue;
		
		return this.value;
	}
	
	public E deref() {
		if (BlockingTransactionManager.isInTransaction()) {
			StateDebuger.debug("ACQUIRE READ LOCK");
			
			BlockingTransactionManager.getReadLock(this);
			
			return value;
		}
		else {
			StateDebuger.debug("RETURN VALUE WITHOUT LOCK");
			
			return value;
		}
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object compareTo) {
		if (compareTo == null) {
			throw new NullPointerException();
		}
		
		if (!(compareTo instanceof BlockingRef)) {
			return false;
		}
		
		return id.equals(((BlockingRef<?>)compareTo).id);
	}

	public int compareTo(BlockingRef<E> compareTo) {
		if (compareTo == null) {
			throw new NullPointerException();
		}
		
		return id.compareTo(compareTo.id);
	}
}
