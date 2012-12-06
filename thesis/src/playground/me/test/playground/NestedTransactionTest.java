package me.test.playground;

import java.util.concurrent.Callable;

import me.test.transactions.ClojureRef;
import me.test.util.debug.Out;
import clojure.lang.LockingTransaction;

public class NestedTransactionTest {

	private static final ClojureRef<Integer> i = new ClojureRef<Integer>(null);
	
	public static void main(String[] args) {
		
		nestedTransactionTest(false);
		
		Out.separator();
		
		nestedTransactionTest(true);
		
	}

	private static void nestedTransactionTest(final boolean rethrow) {
		
		try {
			LockingTransaction.runInTransaction(new Callable<Void>() {
				public Void call() throws Exception {
					i.updateValue(0);
					return null;
				}
			});
		} catch (Exception e) {
			Out.println("INITIALIZATION FAILED");
			return;
		}
		
		Out.println("INITIALLY " + i.deref());
		
		try {
			LockingTransaction.runInTransaction(new Callable<Void>() {

				public Void call() throws Exception {
					i.updateValue(2);
					
					Out.println("AFTER UPDATE 1 " + i.deref());
					
					try {
						LockingTransaction.runInTransaction(new Callable<Void>() {

							public Void call() throws Exception {
								i.updateValue(3);
								
								Out.println("AFTER UPDATE 2 " + i.deref());
								
								throw new NullPointerException();
							}
							
						});
					}
					catch (Exception e) {
						Out.println("EXCEPTION 1 " + i.deref());
						
						if (rethrow) {
							throw new Exception(e);
						}
					}
					finally {
						Out.println("FINALLY 1 " + i.deref());
						
					}
					
					Out.println("AFTER ROLLBACK " + i.deref());
					
					return null;
				}
				
			});
		} catch (Exception e) {
			Out.println("EXCEPTION 2 " + i.deref());
			
		}
		finally {
			Out.println("FINALLY 2 " + i.deref());
		}
		
		Out.println("FINALLY " + i.deref());
	}
}
