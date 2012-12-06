package me.test.util.concurrent;

import java.util.concurrent.ConcurrentMap;

import me.test.transactions.Passable;

public final class ConcurrentMapUtil {

	public static void updateOptimistically2(ConcurrentMap<Integer, Integer> map, Integer key, Passable<Integer> passable) {
		
		Integer oldValue = map.get(key);
		
		while (!map.replace( 
				key, 
				oldValue, 
				passable.call(oldValue))) {
			
			oldValue = map.get(key);
		}
	}
	
	public static <K, V> void updateOptimistically(ConcurrentMap<K, V> map, K key, Passable<V> passable) {
		
		V oldValue = map.get(key);
		
		while (!map.replace( 
				key, 
				oldValue, 
				passable.call(oldValue))) {
			
			oldValue = map.get(key);
		}
	}
}
