package me.test.test.collection.ver1;

import java.util.Collection;
import java.util.List;

import me.test.test.Test;

import org.pcollections.ConsPStack;
import org.pcollections.PStack;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

public final class TreePVectorTest extends AbstractListTest {
	
	@Override
	protected String groupName() {
		return "collection#org.pcollections.TreePVector";
	}
	
	private static PVector<Integer> createList(int testCount) {
		PVector<Integer> list = TreePVector.empty();
		 
		for (int i = 0; i < testCount; i++) {
		
			list = list.plus(Integer.valueOf(0));
		}
		
		return list;
	}
	
	@Override
	Test sequentalFill() {
		return new Test() {
			
			public void prepare(int testCount) {}

			public void run(int testCount) {
				PVector<Integer> list = TreePVector.empty();
				 
				for (int i = 0; i < testCount; i++) {
				
					list = list.plus(Integer.valueOf(0));
				}
			}
			
		};
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
			private PVector<Integer> list;
			
			public void prepare(int testCount) {
				list = createList(testCount);
			}					
	
			public void run(int testCount) {
				
				for (@SuppressWarnings("unused") Integer i : reverse(list)) {
					
				}
			}
			
			private <E> PStack<E> reverse(final Collection<? extends E> list) {
                PStack<E> rev = ConsPStack.empty();
                for(E e : list)
                        rev = rev.plus(e);
                return rev;
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
				List<Integer> copy = list;
				
			}
			
		};
	}

	public TreePVectorTest() {
		super();
	}

}