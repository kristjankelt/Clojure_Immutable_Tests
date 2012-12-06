package me.test.test.collection.ver1;

import java.util.List;

import me.test.test.Test;
import clojure.lang.ISeq;
import clojure.lang.PersistentVector;

public abstract class AbstractPersitentVectorTest extends AbstractListTest {

	@Override
	protected String groupName() {
		return "collection#clojure.lang.PersistentVector";
	}
	
	private static PersistentVector createList(int testCount) {
		PersistentVector list = PersistentVector.create();
	
		for (int i = 0; i < testCount; i++) {
		
			list = list.cons(Integer.valueOf(0));
		}
		
		return list;
	}

	@Override
	protected Test sequentalRead() {
		return new Test() {
			private List<Integer> list;
			
			@SuppressWarnings("unchecked")
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
			private PersistentVector list;
			
			public void prepare(int testCount) {
				list = createList(testCount);
			}					
	
			public void run(int testCount) {
				
				ISeq seq = list.rseq();
				
				while  ((seq = seq.next()) != null) {
					
				}
				
			}
			
		};
	}

	@Override
	protected Test randomRead() {
		return new Test() {
			private PersistentVector list;
			
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
			private PersistentVector list;
			
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
			private PersistentVector list;
			
			public void prepare(int testCount) {
				list = createList(testCount);	
			}					
	
			public void run(int testCount) {
				 
				@SuppressWarnings("unused")
				PersistentVector copy = list;
				
			}
			
		};
	}

	public AbstractPersitentVectorTest() {
		super();
	}

}