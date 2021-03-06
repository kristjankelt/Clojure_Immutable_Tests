package me.test.transactions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

public class OptimisticTransactionManager2 {
	
	private static final ThreadLocal<Object> isInTransaction = new ThreadLocal<Object>();
	
	private static final ThreadLocal<Map<OptimisticRef2<?>, OptimisticRef2<?>>> registeredRefs = new ThreadLocal<Map<OptimisticRef2<?>, OptimisticRef2<?>>>();

	public static <V> V transaction(Callable<V> transaction) {
		
		if (isInTransaction.get() != null) {
			throw new IllegalStateException("Nested transactions are not allowed");
		}
		
		//StateDebuger.debug("START TRANSACTION");
		
		isInTransaction.set(Boolean.TRUE);
		
		registeredRefs.set(new HashMap<OptimisticRef2<?>, OptimisticRef2<?>>());
		
		V returnValue = null; 
		
		@SuppressWarnings("unused")
		int tries = 1;
		
		try {
			while (true) {
				
				Exception encounteredException = null;
				
				//StateDebuger.debug("TRY NR. " + tries);
				
				try {
					returnValue = transaction.call();
					
				} 
				catch (DataChangedException e) {
					//StateDebuger.debug("IN UPDATE RESET AND START OVER");
					reset();
					tries++;
					continue;
				}
				catch (Exception e) {
					encounteredException = e;
				}
				
				if (encounteredException != null) {
					//StateDebuger.debug("EXCEPTION, START CLEAN UP");
					
					clearAll();

					//encounteredException.printStackTrace();
					
					//StateDebuger.debug("RETHROW EXCEPTION");
					
					throw new RuntimeException(encounteredException);
				}
				else {
					
					//StateDebuger.debug("TRY COMMIT");
					if (tryCommit()) {
						//StateDebuger.debug("FINISH COMMIT");
						
						break;
					}
					else {
						//StateDebuger.debug("RESET AND START OVER");
						reset();
						tries++;
						continue;
					}
				}
			}
		}
		finally {
			//StateDebuger.debug("CLEAN UP TRANSACTION");
			finish();
			isInTransaction.set(null);
			registeredRefs.set(null);
		}
		
		//StateDebuger.debug("RETURN TRANSACTION value " + returnValue);
		
		return returnValue;
	}
	
	private static void reset() {
				
		for (OptimisticRef2<?> ref : registeredRefs.get().keySet()) {
			ref.reset();
		}
	}

	private static void finish() {
		for (OptimisticRef2<?> ref : registeredRefs.get().keySet()) {
			ref.finish();
		}
	}

	private static boolean tryCommit() {
		
		final Iterator<OptimisticRef2<?>> iterator = registeredRefs.get().keySet().iterator();
		
		//StateDebuger.debug("TRY COMMIT TRANSACTION (tryCommit)");
		
		if (iterator.hasNext()) {
			
			//StateDebuger.debug("REFs found (tryCommit)");
			
			return iterator.next().tryCommit(iterator);
			
		}
		else {
			//StateDebuger.debug("REFs not found (tryCommit)");
			
			return true;
		}
		
	}

	private static void clearAll() {
		for (OptimisticRef2<?> ref : registeredRefs.get().keySet()) {
			ref.clear();
		}
	}

	public static void registerRef(OptimisticRef2<?> ref) {
		
		registeredRefs.get().put(ref, ref);
		
//		if (!registeredRefs.get().contains(ref)) {
//			registeredRefs.get().add(ref);
//		}
	}
	
	public static boolean isInTransaction() {
		
		if ( isInTransaction.get() == null) {
			return false;
		} 
		
		return true;
	}
}