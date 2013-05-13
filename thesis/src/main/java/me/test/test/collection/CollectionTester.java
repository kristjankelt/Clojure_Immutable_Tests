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
	private long sum;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set<TestContainer> getTests(final Class<? extends CollectionTest> collectionTest) {
		
		try {
			return Collections.unmodifiableSet(new HashSet<TestContainer>() {
				private static final long serialVersionUID = 1L; 
				{
					// GC will be "happy"
					String groupName = collectionTest.newInstance().groupName();
					
					add(new TestItem("sequentialFill", groupName, "collection",  sequentialFill((CollectionTest<Integer>) collectionTest.newInstance())));
					add(new TestItem("sequentialFastFill", groupName, "collection", sequentialFastFill((CollectionTest<Integer>) collectionTest.newInstance())));
					add(new TestItem("randomRead", groupName, "collection", randomRead((CollectionTest<Integer>) collectionTest.newInstance())));
					add(new TestItem("randomUpdate", groupName, "collection", randomUpdate((CollectionTest<Integer>) collectionTest.newInstance())));
					add(new TestItem("sequentialRead", groupName, "collection", sequentialRead((CollectionTest<Integer>) collectionTest.newInstance())));
					add(new TestItem("reverseSequentialRead", groupName, "collection", reverseSequentialRead((CollectionTest<Integer>) collectionTest.newInstance())));
					
					add(new TestItem("calculateSize", groupName, "collection", calculateSize((CollectionTest<Integer>) collectionTest.newInstance())));
					add(new TestItem("createCopy", groupName, "collection", createCopy((CollectionTest<Integer>) collectionTest.newInstance())));
				
					add(new TestItem("sequentialHardFill", groupName, "collection", sequentialHardFill((CollectionTest<BigInteger>) collectionTest.newInstance())));
					add(new TestItem("sequentialLightFill", groupName, "collection", sequentialLightFill((CollectionTest<Integer>) collectionTest.newInstance())));				
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

	
	private Test sequentialFill(final CollectionTest<Integer> collectionTest) {
		return new Test() {
			
			public void prepare(final int testSize) {
				collectionTest.prepareTestEmpty();
			}

			public void run(final int testSize) {
				collectionTest.normalFill(testSize);
			}
			
		};
	}
	
	private Test sequentialLightFill(final CollectionTest<Integer> collectionTest) {
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
	
	private Test sequentialHardFill(final CollectionTest<BigInteger> collectionTest) {
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
	
	private Test sequentialFastFill(final CollectionTest<Integer> collectionTest) {
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
				sum = 0;
				
				for (int i=0; i < testSize; i++) {
					sum = sum + collectionTest.readElement(i).intValue();
				}
				  
			}
			
		};
	}
	
	private Test randomUpdate(final CollectionTest<Integer> collectionTest) {
		return new Test() {
			
			public void prepare(final int testSize) {
				collectionTest.readSafeLimit(testSize);
				
				collectionTest.prepareTest(createIntegerList(testSize));
			}

			public void run(final int testSize) {
				for (int i=0; i < testSize; i++) {
					collectionTest.changeElement(i, Integer.valueOf(1));
				}
			}
			
		};
	}

	
	private Test sequentialRead(final CollectionTest<Integer> collectionTest) {
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
	
	private Test reverseSequentialRead(final CollectionTest<Integer> collectionTest) {
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
			fill[i] = Integer.valueOf(i);
		}
		return Arrays.asList(fill);
	}

}
