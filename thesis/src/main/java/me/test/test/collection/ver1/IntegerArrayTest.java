package me.test.test.collection.ver1;

import java.util.Arrays;
import java.util.Collections;

import me.test.test.Test;

public final class IntegerArrayTest extends AbstractListTest  {

	@Override
	String groupName() {
		return "collection#Integer[]";
	}

	@Override
	Test sequentalFill() {
		return new Test() {
			
			public void prepare(int testCount) {}

			public void run(int testCount) {
				
				Integer[] list = new Integer[testCount];
				
				for (int i = 0; i < testCount; i++) {
				
					list[i] = Integer.valueOf(0);
				}
			}
			
		};
	}

	@Override
	Test sequentalRead() {
		return new Test() {
			private Integer[] list;
			
			public void prepare(int testCount) {
				list = createList(testCount);
			}					

			public void run(int testCount) {
				 
				for (@SuppressWarnings("unused") Integer i : list) {
					
				}
				
			}
			
		};
	}

	@Override
	Test reverseSequentalRead() {
		return new Test() {
			private Integer[] list;
			
			public void prepare(int testCount) {
				list = createList(testCount);
			}					

			public void run(int testCount) {
				Integer[] reversedList = new Integer[testCount];
				
				System.arraycopy(list, 0, reversedList, 0, testCount);
				
				Collections.reverse(Arrays.asList(reversedList));
										
				for (@SuppressWarnings("unused") Integer i : reversedList) {
					
				}
				
			}
			
		};
	}

	@Override
	Test randomRead() {
		return new Test() {
			private Integer[] list;
			
			public void prepare(int testCount) {
				list = createList(testCount);	
			}					

			public void run(int testCount) {
				 
				for (int i = 0; i < testCount; i++) {

					@SuppressWarnings("unused")
					final Integer e = list[i];
				}
				
			}
			
		};
	}

	@Override
	Test computeSize() {
		return new Test() {
			private Integer[] list;
			
			public void prepare(int testCount) {
				list = createList(testCount);	
			}					

			public void run(int testCount) {
				 
				@SuppressWarnings("unused")
				int i = list.length;
				
			}
			
		};
	}
	
	@Override
	Test createCopy() {
		return new Test() {
			private Integer[] list;
			
			public void prepare(int testCount) {
				list = createList(testCount);	
			}					

			public void run(int testCount) {
				 
				Integer[] copy = new Integer[list.length];
				
				System.arraycopy(list, 0, copy, 0, list.length);
				
			}
			
		};
	}

	private static Integer[] createList(int testCount) {
		Integer[] list = new Integer[testCount];
		
		for (int i = 0; i < testCount; i++) {
		
			list[i] = Integer.valueOf(0);
		}
		
		return list;
	}
	
}
