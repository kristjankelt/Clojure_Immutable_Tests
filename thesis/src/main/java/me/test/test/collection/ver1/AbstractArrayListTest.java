package me.test.test.collection.ver1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.test.test.Test;

public abstract class AbstractArrayListTest extends AbstractListTest {

	private static List<Integer> createList(int testCount) {
		List<Integer> list = new ArrayList<Integer>(testCount);
		
		for (int i = 0; i < testCount; i++) {
		
			list.add(Integer.valueOf(0));
		}
		
		return list;
	}

	@Override
	protected String groupName() {
		return "collection#java.util.ArrayList";
	}

	@Override
	protected Test sequentalRead() {
		return new Test() {
			private List<Integer> list;
			
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
	protected Test reverseSequentalRead() {
		return new Test() {
			private List<Integer> list;
			
			public void prepare(int testCount) {
				list = createList(testCount);
			}					
	
			public void run(int testCount) {
				List<Integer> reversedList = new ArrayList<Integer>(list);
				
				Collections.reverse(reversedList);
				
				for (@SuppressWarnings("unused") Integer i : reversedList) {
					
				}
				
			}
			
		};
	}

	@Override
	protected Test randomRead() {
		return new Test() {
			private List<Integer> list;
			
			public void prepare(int testCount) {
				list = createList(testCount);	
			}					
	
			public void run(int testCount) {
				 
				for (int i = 0; i < testCount; i++) {
	
					list.get(i);
				}
				
			}
			
		};
	}

	@Override
	protected Test computeSize() {
		return new Test() {
			private List<Integer> list;
			
			public void prepare(int testCount) {
				list = createList(testCount);	
			}					
	
			public void run(int testCount) {
				 
				list.size();
				
			}
			
		};
	}
	
	@Override
	protected Test createCopy() {
		return new Test() {
			private List<Integer> list;
			
			public void prepare(int testCount) {
				list = createList(testCount);	
			}					
	
			public void run(int testCount) {
				 
				@SuppressWarnings("unused")
				List<Integer> copy = new ArrayList<Integer>(list);

			}
			
		};
	}

	public AbstractArrayListTest() {
		super();
	}

}