package me.test.playground;

import clojure.lang.MapEntry;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentVector;

public class ClojureCollectionTest {
	
	public static void main(String[] args) {
		
		PersistentVector vector1 = PersistentVector.create(1, 1, 1);
		
		PersistentVector vector2 = vector1.cons(4);
		
		PersistentVector vector3 = vector2.pop();
		
		System.out.println(vector1 + "\n" + vector2 + "\n" + vector3);
		
		PersistentHashMap map1 = PersistentHashMap.create(
					new  MapEntry("one", 1), 
					new  MapEntry("two", 2)/*, 
					new  MapEntry("tree", 3)*/);
		
		PersistentHashMap map2 = (PersistentHashMap) map1.cons(new  MapEntry("tree", 3));
		
		System.out.println(map1 + "\n" + map2);
	}
}
