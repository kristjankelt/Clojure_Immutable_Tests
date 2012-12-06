package me.test.transactions;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class SimpleRef<E> {
	
	private final Lock lock = new ReentrantLock();
	
	private E value;
	
	public SimpleRef(E newValue) {
		this.value = newValue;
	}

	// BAD IDEA, DO NOT DO THIS IN REAL LIFE!
	// THE PROBLEM WITH THIS METHOD IS THAT IT COULD BE CALLED RECURSIVELY
	// (MAKING STATE OF value QUESTIONABLE)
	// EVEN FROM THE SEPARATE THREAD (THIS WILL CAUSE DEAD LOCK).
	// IT IS PITTY BECAUSE THIS SOLUTION IS VERY SIMPLE, FAST AND UNIVERSAL
	// (IT COULD BE POSSIBLE USED WHEN THE CONSENCUENCES ARE WELL UNDERSTOOD AND AVOIDED)
	public synchronized E updateValue(Passable<E> update) {
		
		value = update.call(value);
		
		return value;
	}
	
	public synchronized E deref() {
		return value;
	}
	
	// THIS CODE WILL AVOID DEADLOCK BUT WILL NOT ALLOW SIDE EFFECTS 
	// THESE SHOULD BE AVOIDED ANYWAY
	public E updateValueOptimistically(Passable<E> update) {
		
		E savedValue = value;
		
		while (true) {
			E returnedValue = update.call(savedValue);
		
			lock.lock();
			
			try {
				if (value != savedValue) {
					savedValue = value;
					continue;
				}
				else {
					value = returnedValue;
					break;
				}
			}
			finally {
				lock.unlock();
			}
		}
		
		return value;
	}

}
