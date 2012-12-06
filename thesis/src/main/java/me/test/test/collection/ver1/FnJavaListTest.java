package me.test.test.collection.ver1;

import me.test.test.Test;

public final class FnJavaListTest extends AbstractListTest  {

	@Override
	String groupName() {
		return "2.collection#fj.data.List";
	}

	@Override
	Test sequentalFill() {
		return new Test() {
			
			public void prepare(int testCount) {}

			public void run(int testCount) {
				fj.data.List<Integer> list = fj.data.List.list();
				 
				for (int i = 0; i < testCount; i++) {
				
					list = list.cons(Integer.valueOf(0));
				}
			}
			
		};
	}

	@Override
	Test sequentalRead() {
		return new Test() {
			private fj.data.List<Integer> list;
			
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
			private fj.data.List<Integer> list;
			
			public void prepare(int testCount) {
				list = createList(testCount);
			}					

			public void run(int testCount) {
				 
				
				for (@SuppressWarnings("unused") Integer i : list.reverse()) {
					
				}
				
			}
			
		};
	}

	@Override
	Test randomRead() {
		return new Test() {
			private fj.data.List<Integer> list;
			
			public void prepare(int testCount) {
				list = createList(testCount);	
			}					

			public void run(int testCount) {
				 
				for (int i = 0; i < testCount; i++) {

					list.index(i);
				}
				
			}
			
		};
	}

	@Override
	Test computeSize() {
		return new Test() {
			private fj.data.List<Integer> list;
			
			public void prepare(int testCount) {
				list = createList(testCount);	
			}					

			public void run(int testCount) {
				 
				@SuppressWarnings("unused")
				int size = list.length();
				//System.out.println(size);
			}
			
		};
	}
	
	@Override
	Test createCopy() {
		return new Test() {
			private fj.data.List<Integer> list;
			
			public void prepare(int testCount) {
				list = createList(testCount);	
			}					

			public void run(int testCount) {
				@SuppressWarnings("unused")
				fj.data.List<Integer> copy = list;
				
			}
			
		};
	}

	private static fj.data.List<Integer> createList(int testCount) {
		fj.data.List<Integer> list = fj.data.List.list();
		
		for (int i = 0; i < testCount; i++) {
			
			list = list.cons(Integer.valueOf(0));
		}
		
		return list;
	}
	
}
