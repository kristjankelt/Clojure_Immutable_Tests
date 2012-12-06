package me.test.playground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import me.test.util.debug.Out;

public class AnonymousInstance2 {

	public static void main(String[] args) {
		
		final List<Integer> immutableList1 = 
			Collections.unmodifiableList(
				new ArrayList<Integer>() {

					private static final long serialVersionUID = 1L;
					
					{
						add(0);
						add(1);
					}
				}
		);
		
		final List<Integer> immutableList2 = Arrays.asList(0, 1);
				
		Out.println(immutableList1);
		
		Out.println(immutableList2);
		
	}
	
}
