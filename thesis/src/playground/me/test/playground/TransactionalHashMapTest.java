package me.test.playground;

import java.util.concurrent.Callable;

import me.test.util.debug.Out;
import clojure.lang.LockingTransaction;
import clojure.lang.TransactionalHashMap;

public class TransactionalHashMapTest {
	
	private static final TransactionalHashMap<Integer, Integer> map = new TransactionalHashMap<Integer, Integer>();

	public static void main(String[] args) {
		
		try {
			LockingTransaction.runInTransaction(new Callable<Void>() {
				public Void call() throws Exception {
					
					map.put(1, 1);
					
					return null;
				}
			});
		} catch (Exception e) {
			Out.println("INITIALIZATION FAILED");
			return;
		}
		
		System.out.println(map);
	}
}
