package me.test.transactions;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

//import me.test.util.debug.StateDebuger;

public class OptimisticTransactionManager {
	
	private static final ThreadLocal<Object> isInTransaction = new ThreadLocal<Object>();
	
	private static final ThreadLocal<Map<OptimisticRef<?>, OptimisticRef<?>>> registeredRefs = new ThreadLocal<Map<OptimisticRef<?>, OptimisticRef<?>>>();

	public static <V> V transaction(Callable<V> transaction) {
		
		if (isInTransaction.get() != null) {
			throw new IllegalStateException("Nested transactions are not allowed");
		}
		
//		StateDebuger.debug("START TRANSACTION");
		
		isInTransaction.set(Boolean.TRUE);
		
		registeredRefs.set(new TreeMap<OptimisticRef<?>, OptimisticRef<?>>());
		
		V returnValue = null;
		
		@SuppressWarnings("unused")
		int tries = 1;
		
		try {
			while (true) {
				
				Exception encounteredException = null;
				
//				StateDebuger.debug("TRY NR. " + tries);
				
				try {
					returnValue = transaction.call();
					
				} 
				catch (DataChangedException e) {
//					StateDebuger.debug("IN UPDATE RESET AND START OVER");
					reset();
					tries++;
					continue;
				}
				catch (Exception e) {
					encounteredException = e;
				}
				
				if (encounteredException != null) {
//					StateDebuger.debug("EXCEPTION, START CLEAN UP");
					
					clearAll();

					//encounteredException.printStackTrace();
					
//					StateDebuger.debug("RETHROW EXCEPTION");
					
					throw new RuntimeException(encounteredException);
				}
				else {
					
//					StateDebuger.debug("TRY COMMIT");
					if (tryCommit()) {
//						StateDebuger.debug("FINISH COMMIT");
						finish();
						break;
					}
					else {
//						StateDebuger.debug("RESET AND START OVER");
						reset();
						tries++;
						continue;
					}
				}
			}
		}
		finally {
//			StateDebuger.debug("CLEAN UP TRANSACTION");
			isInTransaction.set(null);
			registeredRefs.set(null);
		}
		
//		StateDebuger.debug("RETURN TRANSACTION value " + returnValue);
		
		return returnValue;
	}
	
	private static void reset() {
				
		for (OptimisticRef<?> ref : registeredRefs.get().keySet()) {
			ref.reset();
		}
	}

	private static void finish() {
		for (OptimisticRef<?> ref : registeredRefs.get().keySet()) {
			ref.finish();
		}
	}

	private static boolean tryCommit() {
		for (OptimisticRef<?> ref : registeredRefs.get().keySet()) {
			if (!ref.commit()) {
				return false;
			}
		}
		return true;
	}

	private static void clearAll() {
		for (OptimisticRef<?> ref : registeredRefs.get().keySet()) {
			ref.clear();
		}
	}

	public static void registerRef(OptimisticRef<?> ref) {
		
		registeredRefs.get().put(ref, ref);
		

	}
	
	public static boolean isInTransaction() {
		
		if ( isInTransaction.get() == null) {
			return false;
		} 
		
		return true;
	}
}