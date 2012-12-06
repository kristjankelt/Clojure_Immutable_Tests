package me.test.test.collection.ver1;


import me.test.test.Test;

public final class IntArrayTest extends AbstractListTest  {

	@Override
	String groupName() {
		return "collection#int[]";
	}

	@Override
	Test sequentalFill() {
		return new Test() {
			
			public void prepare(int testCount) {}

			public void run(int testCount) {
				
				int[] list = new int[testCount];
				
				for (int i = 0; i < testCount; i++) {
				
					list[i] = 0;
				}
			}
			
		};
	}

	@Override
	Test sequentalRead() {
		return new Test() {
			private int[] list;
			
			public void prepare(int testCount) {
				list = createList(testCount);
			}					

			public void run(int testCount) {
				 
				for (@SuppressWarnings("unused") int i : list) {
					
				}
				
			}
			
		};
	}

	@Override
	Test reverseSequentalRead() {
		return new Test() {
			private int[] list;
			
			public void prepare(int testCount) {
				list = createList(testCount);
			}					

			public void run(int testCount) {
				int[] reversedList = new int[testCount];
				
				System.arraycopy(list, 0, reversedList, 0, testCount);
				
				reverseOrder(reversedList);
										
				for (@SuppressWarnings("unused") Integer i : reversedList) {
					
				}
				
			}
			
		};
	}

	@Override
	Test randomRead() {
		return new Test() {
			private int[] list;
			
			public void prepare(int testCount) {
				list = createList(testCount);	
			}					

			public void run(int testCount) {
				 
				for (int i = 0; i < testCount; i++) {

					@SuppressWarnings("unused")
					final int e = list[i];
				}
				
			}
			
		};
	}

	@Override
	Test computeSize() {
		return new Test() {
			private int[] list;
			
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
			private int[] list;
			
			public void prepare(int testCount) {
				list = createList(testCount);	
			}					

			public void run(int testCount) {
				 
				int[] copy = new int[list.length];
				
				System.arraycopy(list, 0, copy, 0, list.length);
				
			}
			
		};
	}
	
	private static void reverseOrder(int[] array) {
		int swap;

		for (int i = 0; i < array.length/2; i++) {

			swap = array[i];
			array[i] = array[array.length-1-i];
			array[array.length-1-i] = swap;
		}
	}

	private static int[] createList(int testCount) {
		int[] list = new int[testCount];
		
		for (int i = 0; i < testCount; i++) {
		
			list[i] = 0;
		}
		
		return list;
	}
	
}
