package me.test.transactions2;

import java.util.concurrent.atomic.AtomicInteger;

public final class Ref<E> {
	
	private static final AtomicInteger uniqueId = new AtomicInteger(0);
	
	private Integer id = Integer.valueOf(uniqueId.getAndIncrement());
    
    public String toString() {
    	 return "Ref <" + Integer.toString(id) + ">";
    }
    
    private E value;
    
    protected E getValue() {
    	return value;
    }
    
    protected void setValue(E value) {
    	this.value = value;
    }
    
	public E updateValue(final E newValue) {
		return Transaction.updateValue(this, newValue);
	}
	
	public E deref() {
		return Transaction.deref(this);
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
		
		if (!(compareTo instanceof Ref)) {
			return false;
		}
		
		return id.equals(((Ref<?>)compareTo).id);
	}

	public int compareTo(Ref<E> compareTo) {
		if (compareTo == null) {
			throw new NullPointerException();
		}
		
		return id.compareTo(compareTo.id);
	}
}
