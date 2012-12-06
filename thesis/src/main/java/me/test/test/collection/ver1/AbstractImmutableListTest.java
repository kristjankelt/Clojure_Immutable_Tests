package me.test.test.collection.ver1;

import java.util.ArrayList;
import java.util.List;

import me.test.test.Test;

import com.google.common.collect.ImmutableList;

public abstract class AbstractImmutableListTest extends AbstractListTest {

	private static ImmutableList<Integer> createList(int testCount) {
		List<Integer> list = new ArrayList<Integer>(testCount);
		
	
		for (int i = 0; i < testCount; i++) {
			list.add(
					Integer.valueOf(0));
		}
		
		ImmutableList<Integer> immutableList = ImmutableList.copyOf(list);
		
		
		return immutableList;
	}

	@Override
	protected String groupName() {
		return "collection#com.google.common.collect.ImmutableList";
	}

	@Override
	protected Test sequentalRead() {
		return new Test() {
			private ImmutableList<Integer> list;
			
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
			private ImmutableList<Integer> list;
			
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
	protected Test randomRead() {
		return new Test() {
			private ImmutableList<Integer> list;
			
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
			private ImmutableList<Integer> list;
			
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
			private ImmutableList<Integer> list;
			
			public void prepare(int testCount) {
				list = createList(testCount);	
			}					
	
			public void run(int testCount) {
				 
				@SuppressWarnings("unused")
				ImmutableList<Integer> copy = list;
				
			}
			
		};
	}


	public AbstractImmutableListTest() {
		super();
	}

}