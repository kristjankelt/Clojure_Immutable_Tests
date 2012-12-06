package me.test.playground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clojure.lang.PersistentVector;

public class PersistentVectorTest {
	
	private static class Dummy {
		
	}

	public static void main(String[] args) {
		
		Dummy dummy = new Dummy();
		
		List<Dummy> list = new ArrayList<Dummy>();
		for (int i=0; i < 32000; i++) {
			list.add(dummy);
		}
		
		PersistentVector list1 = PersistentVector
				.create(list);
		
		for (int i=0; i < 32000; i++) {
			list1 = list1.cons(dummy);
		}
		
		
		
	}
}
