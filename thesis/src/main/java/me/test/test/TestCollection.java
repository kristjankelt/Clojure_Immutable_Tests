package me.test.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
 
import me.test.test.collection.ArrayListTest;
import me.test.test.collection.BitmappedTrieTestO2;
import me.test.test.collection.BitmappedTrieTest2;
import me.test.test.collection.BitmappedTrieTestO4;
import me.test.test.collection.BitmappedTrieTestO5;
import me.test.test.collection.BitmappedTrieTest1;
import me.test.test.collection.BitmappedTrieTest64;
import me.test.test.collection.BitmappedTrieTest4;
import me.test.test.collection.BitmappedTrieTest5;
import me.test.test.collection.FastArrayListTest;
import me.test.test.collection.CollectionTester;
import me.test.test.collection.BitmappedTrieTest3;
import me.test.test.collection.FnJavaListTest2;
import me.test.test.collection.HashMapTest;
import me.test.test.collection.ImmutableArrayListTest;
import me.test.test.collection.ImmutableArrayListTest2;
import me.test.test.collection.ImmutableListTest2;
import me.test.test.collection.LinkedListTest;
import me.test.test.collection.PersistentHashMapTest;
import me.test.test.collection.PersistentListTest;
import me.test.test.collection.PersistentQueueTest;
import me.test.test.collection.PersistentTreeMapTest;
import me.test.test.collection.PersistentVectorTest;
import me.test.test.collection.TreeMapTest;
import me.test.test.collection.TreePVectorTest2;
import me.test.test.concurrency.collectiontest.AtomicCollectionTest;
import me.test.test.concurrency.collectiontest.BlockingCollectionTest;
import me.test.test.concurrency.collectiontest.ClojureSTMCollectionTest;
import me.test.test.concurrency.collectiontest.ConcurrentCollectionTester;
import me.test.test.concurrency.collectiontest.NativeAtomicCollectionTest;
import me.test.test.concurrency.collectiontest.NativeClojureCollectionTest;
import me.test.test.concurrency.collectiontest.NativeOptimisticCollectionTest;
import me.test.test.concurrency.collectiontest.OptimisticBitmappedTrieTest;
import me.test.test.concurrency.collectiontest.OptimisticBitmappedTrieTest2;
import me.test.test.concurrency.collectiontest.OptimisticBitmappedTrieTest3;
import me.test.test.concurrency.collectiontest.OptimisticCollectionTest;
import me.test.test.concurrency.collectiontest.OptimisticCollectionTest2;
import me.test.test.concurrency.collectiontest.SynchronizeCollectionTest;
import me.test.test.concurrency.collectiontest.SynchronizeCollectionTest2;
import me.test.test.concurrency.collectiontest.SynchronizeCollectionTest3;
import me.test.test.concurrency.countertest.AtomicCounterTest;
import me.test.test.concurrency.countertest.AtomicCounterTest2;
import me.test.test.concurrency.countertest.BlockingCounterTest;
import me.test.test.concurrency.countertest.ClojureSTMCounterTest;
import me.test.test.concurrency.countertest.ConcurrentTester;
import me.test.test.concurrency.countertest.OptimisticCounterTest;
import me.test.test.concurrency.countertest.OptimisticCounterTest2;
import me.test.test.concurrency.countertest.ReadWriteLockCounterTest;
import me.test.test.concurrency.countertest.SimpleCounterTest;
import me.test.test.concurrency.countertest.SimpleOptimisticCounterTest;
import me.test.test.concurrency.countertest.SynchronizedCounterTest;
import me.test.test.concurrency.countertest.SynchronizedCounterTest2;
import me.test.test.locks.CasLockTest;
import me.test.test.locks.CasLockTest2;
import me.test.test.locks.LockTester;
import me.test.test.locks.ReentrantLockTest;
import me.test.test.locks.SynchronizedLockTest;
import me.test.test.locks.SynchronizedThisLockTest;
import me.test.test.lowlevel.ArrayCopyTest01;
import me.test.test.lowlevel.ArrayCopyTest02;
import me.test.test.lowlevel.ArrayCopyTest03;
import me.test.test.lowlevel.LowLevelTester;
import me.test.util.debug.CountTime;
import me.test.util.debug.Out;

@SuppressWarnings("unused")
public class TestCollection {
	
	private static final Map<String, TestContainer> TESTS = Collections.unmodifiableMap(
		new HashMap<String, TestContainer>() {
			private static final long serialVersionUID = 1L;
			{
				
				addTests(this, (new LowLevelTester()).getTests(ArrayCopyTest01.class));
				addTests(this, (new LowLevelTester()).getTests(ArrayCopyTest02.class));
				addTests(this, (new LowLevelTester()).getTests(ArrayCopyTest03.class));
				
				addTests(this, (new LockTester()).getTests(SynchronizedLockTest.class));
				addTests(this, (new LockTester()).getTests(ReentrantLockTest.class));
				addTests(this, (new LockTester()).getTests(SynchronizedThisLockTest.class));
				addTests(this, (new LockTester()).getTests(CasLockTest.class));
				addTests(this, (new LockTester()).getTests(CasLockTest2.class));
				
				addTests(this, (new CollectionTester()).getTests(ArrayListTest.class));
				addTests(this, (new CollectionTester()).getTests(LinkedListTest.class));
				addTests(this, (new CollectionTester()).getTests(HashMapTest.class));
				addTests(this, (new CollectionTester()).getTests(TreeMapTest.class));
				
				addTests(this, (new CollectionTester()).getTests(PersistentVectorTest.class));
				addTests(this, (new CollectionTester()).getTests(PersistentListTest.class));
				addTests(this, (new CollectionTester()).getTests(PersistentQueueTest.class));
				addTests(this, (new CollectionTester()).getTests(PersistentHashMapTest.class));
				
//				addTests(this, (new CollectionTester()).getTests(PersistentTreeMapTest.class));
				
				addTests(this, (new CollectionTester()).getTests(FastArrayListTest.class));				
				
				addTests(this, (new CollectionTester()).getTests(BitmappedTrieTest1.class));
				addTests(this, (new CollectionTester()).getTests(BitmappedTrieTest2.class));
				addTests(this, (new CollectionTester()).getTests(BitmappedTrieTest3.class));
				addTests(this, (new CollectionTester()).getTests(BitmappedTrieTest4.class));
				addTests(this, (new CollectionTester()).getTests(BitmappedTrieTest5.class));
				
				
//				addTests(this, (new CollectionTester()).getTests(ImmutableArrayListTest.class));
//				addTests(this, (new CollectionTester()).getTests(ImmutableArrayListTest2.class));
//				addTests(this, (new CollectionTester()).getTests(FnJavaListTest2.class));
//				addTests(this, (new CollectionTester()).getTests(ImmutableListTest2.class));
//				addTests(this, (new CollectionTester()).getTests(TreePVectorTest2.class));				
				
				
//				addTests(this, (new ConcurrentCollectionTester()).getTests(BlockingCollectionTest.class));
//				addTests(this, (new ConcurrentCollectionTester()).getTests(NativeClojureCollectionTest.class));
//				addTests(this, (new ConcurrentCollectionTester()).getTests(NativeOptimisticCollectionTest.class));
//				addTests(this, (new ConcurrentCollectionTester()).getTests(NativeAtomicCollectionTest.class));
//				addTests(this, (new ConcurrentCollectionTester()).getTests(OptimisticCollectionTest2.class));

				addTests(this, (new ConcurrentCollectionTester()).getTests(ClojureSTMCollectionTest.class));
				addTests(this, (new ConcurrentCollectionTester()).getTests(OptimisticCollectionTest.class));
				addTests(this, (new ConcurrentCollectionTester()).getTests(SynchronizeCollectionTest.class));
				addTests(this, (new ConcurrentCollectionTester()).getTests(SynchronizeCollectionTest2.class));
				addTests(this, (new ConcurrentCollectionTester()).getTests(SynchronizeCollectionTest3.class));
				addTests(this, (new ConcurrentCollectionTester()).getTests(AtomicCollectionTest.class));
				addTests(this, (new ConcurrentCollectionTester()).getTests(OptimisticBitmappedTrieTest.class));
				addTests(this, (new ConcurrentCollectionTester()).getTests(OptimisticBitmappedTrieTest2.class));
				addTests(this, (new ConcurrentCollectionTester()).getTests(OptimisticBitmappedTrieTest3.class));
				
			
				addTests(this, (new ConcurrentTester()).getTests(AtomicCounterTest.class));
				addTests(this, (new ConcurrentTester()).getTests(AtomicCounterTest2.class));
				//addTests(this, (new ConcurrentTester()).getTests(BlockingCounterTest.class));
				addTests(this, (new ConcurrentTester()).getTests(ClojureSTMCounterTest.class));
				addTests(this, (new ConcurrentTester()).getTests(OptimisticCounterTest.class));
				addTests(this, (new ConcurrentTester()).getTests(OptimisticCounterTest2.class));
//				addTests(this, (new ConcurrentTester()).getTests(SimpleCounterTest.class));
//				addTests(this, (new ConcurrentTester()).getTests(SimpleOptimisticCounterTest.class));
				addTests(this, (new ConcurrentTester()).getTests(SynchronizedCounterTest.class));
				addTests(this, (new ConcurrentTester()).getTests(SynchronizedCounterTest2.class));
				addTests(this, (new ConcurrentTester()).getTests(ReadWriteLockCounterTest.class));
				
				//addTests(this, (new FnJavaListTest()).getTests());
				//addTests(this, (new ArrayListTest()).getTests());
				//addTests(this, (new ArrayListInitialCapacityTest()).getTests());
				//addTests(this, (new ImmutableListTest()).getTests());
				//addTests(this, (new FasterImmutableListTest()).getTests());
				//addTests(this, (new IntArrayTest()).getTests());
				//addTests(this, (new IntegerArrayTest()).getTests());		
				//addTests(this, (new PersistentVectorTest()).getTests());
				//addTests(this, (new TransientPersistentVectorTest()).getTests());
				//addTests(this, (new TreePVectorTest()).getTests());
				
			}
			
		}
	);

	private static void addTests(final Map<String, TestContainer> tests, Set<TestContainer> addedTests) {
		for (TestContainer test : addedTests) {
			if (tests.containsKey(createTestId(test))) {
				throw new IllegalStateException("Duplicate test id " + createTestId(test));
			}
			tests.put(createTestId(test), test);
		}
	}

	private static String createTestId(TestContainer test) {
		return
				test.getSetName() +
				"#" +
				test.getGroupName() + 
				"." +
				test.getTestName();
	}
	
	public static Map<String, TestContainer> getAllTests() {
		return TESTS;
	}
	
}
