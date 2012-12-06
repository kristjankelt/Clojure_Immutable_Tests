package me.test.playground.package1;

import java.util.concurrent.Callable;

import me.test.playground.package1.Ref.UpdateStrategy;
import me.test.playground.package2.OtherUpdateStrategy;

public class Transaction {

	public static <V> void transaction(final Callable<V> transaction) {
		
		@SuppressWarnings("unused")
		V returnValue = null;
		
		try {
			returnValue = transaction.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			
		}
		
		Ref<Long> ref = new Ref<Long>(1L);
		
		UpdateStrategy<Long> stradedgy = new OtherUpdateStrategy<Long>(ref);
		
		stradedgy.updateValue(2L);
		
		System.out.println(ref.getValue());

	}
}
