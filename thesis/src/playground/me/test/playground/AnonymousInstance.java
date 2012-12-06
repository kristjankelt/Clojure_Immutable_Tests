package me.test.playground;

import me.test.playground.package1.ImmutableList;
import me.test.util.debug.Out;

public class AnonymousInstance {

	public static void main(String[] args) {
		
		ImmutableList<Integer> list = new ImmutableList<Integer>() {
			{
				add(0);
				add(1);
			}
			
			@SuppressWarnings("unused")
			public void add2() {
				add(1);
			}
		};
		
		Out.println(list);
		
	}
	
}
