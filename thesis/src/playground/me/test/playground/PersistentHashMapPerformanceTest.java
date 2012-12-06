package me.test.playground;

import java.util.Map;
import java.util.TreeMap;

import me.test.util.debug.CountTime;
import clojure.lang.MapEntry;
import clojure.lang.PersistentHashMap;

public class PersistentHashMapPerformanceTest {

	public static void main(String[] args) {
		
		CountTime.run(new Runnable() {

			public void run() {
				//Map<Integer, Integer> map = new HashMap<Integer, Integer>();
				Map<Integer, Integer> map = new TreeMap<Integer, Integer>();

				for (int i = 0; i < 10000; i++) {
					map.put(Integer.valueOf(i), Integer.valueOf(i));
				}
			}
			
		});
		
		CountTime.run(new Runnable() {

			public void run() {
				PersistentHashMap map = PersistentHashMap.create(); 

				for (int i = 0; i < 10000; i++) {
					map = (PersistentHashMap) map.cons(
						new MapEntry(Integer.valueOf(i), Integer.valueOf(i)));
					
				}
			}
			
		});
	}
}
