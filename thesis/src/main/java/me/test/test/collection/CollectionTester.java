package me.test.test.collection;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.test.test.Test;
import me.test.test.TestContainer;
import me.test.test.TestItem;

public final class CollectionTester {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set<TestContainer> getTests(final Class<? extends CollectionTest> collectionTest) {
		
		try {
			return Collections.unmodifiableSet(new HashSet<TestContainer>() {
				private static final long serialVersionUID = 1L; 
				{
					// GC will be "happy"
					String groupName = collectionTest.newInstance().groupName();
					
					add(new TestItem("sequentalFill", groupName, "collection",  sequentalFill((CollectionTest<Integer>) collectionTest.newInstance())));
					add(new TestItem("sequentalFastFill", groupName, "collection", sequentalFastFill((CollectionTest<Integer>) collectionTest.newInstance())));
					add(new TestItem("sequentalHardFill", groupName, "collection", sequentalHardFill((CollectionTest<BigInteger>) collectionTest.newInstance())));
					add(new TestItem("sequentalLightFill", groupName, "collection", sequentalLightFill((CollectionTest<Integer>) collectionTest.newInstance())));				
					add(new TestItem("randomRead", groupName, "collection", randomRead((CollectionTest<Integer>) collectionTest.newInstance())));
					add(new TestItem("sequentalRead", groupName, "collection", sequentalRead((CollectionTest<Integer>) collectionTest.newInstance())));
					add(new TestItem("reverseSequentalRead", groupName, "collection", reverseSequentalRead((CollectionTest<Integer>) collectionTest.newInstance())));
					add(new TestItem("calculateSize", groupName, "collection", calculateSize((CollectionTest<Integer>) collectionTest.newInstance())));
					add(new TestItem("createCopy", groupName, "collection", createCopy((CollectionTest<Integer>) collectionTest.newInstance())));
					add(new TestItem("smallCalculation", groupName, "collection", smallCalculation((CollectionTest<Integer>) collectionTest.newInstance())));
					
					
				}
			});
		} 
		catch (InstantiationException e) {
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		
	}

	private Test sequentalFill(final CollectionTest<Integer> collectionTest) {
		return new Test() {
			
			public void prepare(final int testSize) {
				collectionTest.prepareTestEmpty();
			}

			public void run(final int testSize) {
				collectionTest.normalFill(testSize);
			}
			
		};
	}
	
	private Test sequentalLightFill(final CollectionTest<Integer> collectionTest) {
		return new Test() {
			
			public void prepare(final int testSize) {
				
				collectionTest.fillSafeLimit(testSize);
				
				collectionTest.prepareTestEmpty();
			}

			public void run(final int testSize) {
				
				for (int i=0; i < testSize; i++) {
					collectionTest.addElement(Integer.valueOf(0));
				}				
			}
			
		};
	}
	
	private Test sequentalHardFill(final CollectionTest<BigInteger> collectionTest) {
		return new Test() {
			
			public void prepare(final int testSize) {
				collectionTest.fillSafeLimit(testSize);
				
				collectionTest.prepareTestEmpty();
			}

			public void run(final int testSize) {
				for (int i=0; i < testSize; i++) {
					collectionTest.addElement(BigInteger.valueOf(i));
				}				
			}
			
		};
	}
	
	private Test sequentalFastFill(final CollectionTest<Integer> collectionTest) {
		return new Test() {
			
			public void prepare(final int testSize) {}

			public void run(final int testSize) {
				collectionTest.fastFill(testSize);
			}
			
		};
	}
	
	private Test randomRead(final CollectionTest<Integer> collectionTest) {
		return new Test() {
			
			public void prepare(final int testSize) {
				collectionTest.readSafeLimit(testSize);
				
				collectionTest.prepareTest(createIntegerList(testSize));
			}

			public void run(final int testSize) {
				for (int i=0; i < testSize; i++) {
					collectionTest.readElement(i);
				}
			}
			
		};
	}
	
	private Test sequentalRead(final CollectionTest<Integer> collectionTest) {
		return new Test() {
			
			public void prepare(final int testSize) {
				
				collectionTest.prepareTest(createIntegerList(testSize));
			}

			public void run(final int testSize) {
				for (@SuppressWarnings("unused") Integer i : collectionTest.iterable(testSize)) {
					
				}
			}
			
		};
	}
	
	private Test reverseSequentalRead(final CollectionTest<Integer> collectionTest) {
		return new Test() {
			
			public void prepare(final int testSize) {
						
				collectionTest.prepareTest(createIntegerList(testSize));
			}

			

			public void run(final int testSize) {
				for (@SuppressWarnings("unused") Integer i : collectionTest.reverseIterable(testSize)) {
					
				}
			}
			
		};
	}
		
	
	private Test calculateSize(final CollectionTest<Integer> collectionTest) {
		return new Test() {
			
			public void prepare(final int testSize) {
				collectionTest.prepareTest(createIntegerList(testSize));
			}

			public void run(final int testSize) {
				collectionTest.calculateSize();
			}
			
		};
	}
	
	private Test createCopy(final CollectionTest<Integer> collectionTest) {
		return new Test() {
			
			public void prepare(final int testSize) {
				collectionTest.prepareTest(createIntegerList(testSize));
			}

			public void run(final int testSize) {
				collectionTest.createCopy();
			}
			
		};
	}
	
	private Test smallCalculation(final CollectionTest<Integer> collectionTest) {
		return new Test() {
			
			public void prepare(final int testSize) {
				collectionTest.prepareTest(createIntegerList(testSize));
			}

			public void run(final int testSize) {
				BigInteger summary = BigInteger.ZERO;
				
				for (Integer i : collectionTest.iterable(testSize)) {
					summary = summary.add(BigInteger.valueOf(i));
				}
			}
			
		};
	}
	
		
	private static List<Integer> createIntegerList(final int testSize) {
		Integer[] fill = new Integer[testSize];
		
		for (int i=0; i < testSize; i++) {
			fill[i] = Integer.valueOf(0);
		}
		return Arrays.asList(fill);
	}

}
