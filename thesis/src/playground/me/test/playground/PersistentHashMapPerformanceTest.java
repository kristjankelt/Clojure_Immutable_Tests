package me.test.playground;

import java.util.Map;
import java.util.TreeMap;

import me.test.util.debug.CountTime;
import clojure.lang.MapEntry;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentVector;

public class PersistentHashMapPerformanceTest {
	
	private static int TEST_SIZE = 1000000;
	
	private static PersistentHashMap createMap() {
		PersistentHashMap map = PersistentHashMap.create(); 

		for (int i = 0; i < TEST_SIZE; i++) {
			map = (PersistentHashMap) map.cons(
				new MapEntry(Integer.valueOf(i), Integer.valueOf(i)));
		}
		
		return map;
	}

	public static void main(String[] args) {
		
		CountTime.run(new Runnable() {

			public void run() {
				Integer[] map = new Integer[TEST_SIZE];

				for (int i = 0; i < TEST_SIZE; i++) {
					map[i] = Integer.valueOf(i);
				}
			}
			
		});
		
		final Integer[] map1 = new Integer[TEST_SIZE];

		for (int i = 0; i < TEST_SIZE; i++) {
			map1[i] = Integer.valueOf(i);
		}
		
		CountTime.run(new Runnable() {

			public void run() {

				
				for (int i = 0; i < TEST_SIZE; i++) {
					Integer key = map1[i];
				}
			}
			
		});
		
		CountTime.run(new Runnable() {

			public void run() {
				Map<Integer, Integer> map = new TreeMap<Integer, Integer>();

				for (int i = 0; i < TEST_SIZE; i++) {
					map.put(Integer.valueOf(i), Integer.valueOf(i));
				}

			}
			
		});
		
		final Map<Integer, Integer> map2 = new TreeMap<Integer, Integer>();

		for (int i = 0; i < TEST_SIZE; i++) {
			map2.put(Integer.valueOf(i), Integer.valueOf(i));
		}
		
		CountTime.run(new Runnable() {

			public void run() {
				//Map<Integer, Integer> map = new HashMap<Integer, Integer>();
				
				
				for (int i = 0; i < TEST_SIZE; i++) {
					Integer key = map2.get(Integer.valueOf(i));
				}
			}
			
		});
		
		System.out.println("################");
		
		CountTime.run(new Runnable() {

			public void run() {
				PersistentHashMap map4 = PersistentHashMap.create();
				
				for (int i = 0; i < TEST_SIZE; i++) {
					map4 = (PersistentHashMap) map4.cons(
						new MapEntry(Integer.valueOf(i), Integer.valueOf(i)));
				}
			}
			
		});
		
		System.out.println("################");
		
		final PersistentHashMap map3 = createMap();
		
		CountTime.run(new Runnable() {

			public void run() {
				PersistentHashMap map4 = map3;

				for (int i = 0; i < TEST_SIZE; i++) {
					Integer key = (Integer) map4.get(Integer.valueOf(i));
				}
			}
			
		});
		
		CountTime.run(new Runnable() {

			public void run() {
				PersistentVector newList = PersistentVector.create();
				
				for (int i=0; i < TEST_SIZE; i++) {
					newList = newList.cons(Integer.valueOf(i));
				}
			}
			
		});
		
		
	}
}
