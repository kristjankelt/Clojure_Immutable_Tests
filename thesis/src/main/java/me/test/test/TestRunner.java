package me.test.test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import me.test.test.concurrency.collectiontest.BlockingCollectionTest;
import me.test.test.concurrency.collectiontest.ClojureSTMCollectionTest;
import me.test.test.concurrency.collectiontest.ConcurrentCollectionTest;
import me.test.test.concurrency.collectiontest.NativeOptimisticCollectionTest;
import me.test.test.concurrency.collectiontest.OptimisticCollectionTest;
import me.test.test.concurrency.collectiontest.SynchronizeCollectionTest;
import me.test.test.concurrency.countertest.AtomicCounterTest;
import me.test.test.concurrency.countertest.BlockingCounterTest;
import me.test.test.concurrency.countertest.ClojureSTMCounterTest;
import me.test.test.concurrency.countertest.CounterTest;
import me.test.test.concurrency.countertest.OptimisticCounterTest;
import me.test.test.concurrency.countertest.OptimisticCounterTest2;
import me.test.test.concurrency.countertest.SimpleCounterTest;
import me.test.test.concurrency.countertest.SimpleOptimisticCounterTest;
import me.test.test.concurrency.countertest.SynchronizedCounterTest;
import me.test.transactions.Passable;
import me.test.util.concurrent.ConcurrentMapUtil;
import me.test.util.concurrent.ParallelRunner;
import me.test.util.debug.CountTime;
import me.test.util.debug.Out;
import me.test.util.debug.StateDebuger;
import clojure.lang.LockingTransaction;
import clojure.lang.TransactionalHashMap;

/**
 * This is the first version of the TestRunner. This version is kept for reference because  
 * some of the tests here are not implemented in the new version.
 */
public class TestRunner {
	
	private static enum TEST {
		OPTIMISTIC(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				doCounting((Integer)parameters[0], new OptimisticCounterTest(), false);
			}
		}),
		OPTIMISTIC2(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				doCounting((Integer)parameters[0], new OptimisticCounterTest(), true);
			}
		}),
		OPTIMISTIC3(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				doCounting((Integer)parameters[0], new OptimisticCounterTest2(), true);
			}
		}),
		BLOCKING(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				doCounting((Integer)parameters[0],  new BlockingCounterTest(), false);
				
			}
		}),
		BLOCKING2(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				doCounting((Integer)parameters[0],  new BlockingCounterTest(), true);
				
			}
		}),
		CLOJURE(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				doCounting((Integer)parameters[0],  new ClojureSTMCounterTest(), false);
				
			}
		}),
		CLOJURE2(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				doCounting((Integer)parameters[0],  new ClojureSTMCounterTest(), true);
				
			}
		}),
		SYNCHRONIZED(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				doCounting((Integer)parameters[0],  new  SynchronizedCounterTest(), false);
				
			}
		}),
		SYNCHRONIZED2(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				doCounting((Integer)parameters[0],  new  SynchronizedCounterTest(), true);
				
			}
		}),
		ATOMIC(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
								
				doCounting((Integer)parameters[0],  new  AtomicCounterTest(), false);
				
			}
		}),
		SIMPLE(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				doCounting((Integer)parameters[0],  new  SimpleCounterTest(), false);
				
			}
		}),
		SIMPLEOPTIMISTIC(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				doCounting((Integer)parameters[0],  new  SimpleOptimisticCounterTest(), false);
				
			}
		}),
		SINGLETHREAD(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				long counter = 0;
				
				final int runCount = (Integer)parameters[0];
				
				for (int i=0; i < 2 * runCount; i++) {
					counter++;	
				}
				
				System.out.println("FINAL COUNTER VALUE is " + counter);
			}
		}),
		READWRITESYNCHRONIZED(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				
				final int runCount = (Integer)parameters[0];
				
				doReadWrite(runCount, new SynchronizedCounterTest(), false);
			}
		}),
		READWRITEBLOCKING(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				
				final int runCount = (Integer)parameters[0];
				
				doReadWrite(runCount, new BlockingCounterTest(), false);
			}
		}),
		READWRITEOPTIMISTIC(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				
				final int runCount = (Integer)parameters[0];
				
				doReadWrite(runCount, new OptimisticCounterTest(), false);
			}
		}),
		READWRITECLOJURE(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				
				final int runCount = (Integer)parameters[0];
				
				doReadWrite(runCount, new ClojureSTMCounterTest(), false);
			}
		}),
		COLLECTIONBLOCKING(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				
				final int runCount = (Integer)parameters[0];
				
				doCollection(runCount, new BlockingCollectionTest());
			}
		}),
		COLLECTIONOPTIMISTIC(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				
				final int runCount = (Integer)parameters[0];
				
				doCollection(runCount, new OptimisticCollectionTest());
			}
		}),
		COLLECTIONCLOJURE(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				
				final int runCount = (Integer)parameters[0];
				
				doCollection(runCount, new ClojureSTMCollectionTest());
			}
		}),
		COLLECTIONNATIVEOPTIMISTIC(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				
				final int runCount = (Integer)parameters[0];
				
				doCollection(runCount, new NativeOptimisticCollectionTest());
			}
		}),
		COLLECTIONSYNCHRONIZED(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				
				final int runCount = (Integer)parameters[0];
				
				doCollection(runCount, new SynchronizeCollectionTest());
			}
		}),
		CONCURRENTMAP(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				
				final int runCount = (Integer)parameters[0];
				
				final ConcurrentMap<Integer, Integer> map = 
									new ConcurrentHashMap<Integer, Integer>();
				
				
				ParallelRunner.run(new Runnable() {
					public void run() {
						for (int i=0; i < runCount * 2; i++) {
							
							map.putIfAbsent(Integer.valueOf(i), Integer.valueOf(i));
						}
						
					}},
					new Runnable() {
						public void run() {
							//for (int i= runCount ; i < runCount * 2; i++) {
							for (int i=0; i < runCount * 2; i++) {
								
								
								map.putIfAbsent(Integer.valueOf(i), Integer.valueOf(i));
								
							}
							
						}});	
			}
			
			
		}),
		CONCURRENTMAP2(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				
				final int runCount = (Integer)parameters[0];
				
				final Integer KEY = Integer.valueOf(1);
				
				final ConcurrentMap<Integer, Integer> map = 
									new ConcurrentHashMap<Integer, Integer>();
				
				map.put(KEY, 0);
				
				ParallelRunner.run(2,
					new Runnable() {
						public void run() {
							//for (int i= runCount ; i < runCount * 2; i++) {
							for (int i=0; i < runCount; i++) {
								
								
								ConcurrentMapUtil.updateOptimistically(map, KEY, new Passable<Integer>() {
									public Integer call(Integer oldValue) {
										return Integer.valueOf(oldValue.intValue() + 1);
									}
								});
								
//								Integer oldValue = map.get(KEY);
//								
//								while (!map.replace(
//										KEY, 
//										oldValue, 
//										Integer.valueOf(oldValue.intValue()+1))) {
//									
//									oldValue = map.get(KEY);
//								}
								
							}
							
						}});	
				
				
			
				System.out.println(map);
			}			
			
		}),
		TRANSACTIONALMAP(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				
				final int runCount = (Integer)parameters[0];
				
				final ConcurrentMap<Integer, Integer> map = 
									new TransactionalHashMap<Integer, Integer>();
				
				
				ParallelRunner.run(2, new Runnable() {
					public void run() {
						for (int i=0; i < runCount * 2; i++) {
							try {
								final int h = i;
								LockingTransaction.runInTransaction(new Callable<Void>() {
									public Void call() throws Exception {
										map.putIfAbsent(Integer.valueOf(h), Integer.valueOf(h));
										return null;
									}
								});
							} catch (Exception e) {

								e.printStackTrace();
							}
							
						}
						
					}});	
			}
			
			
		}),
		TRANSACTIONALMAP2(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				
				final int runCount = (Integer)parameters[0];
				
				final Integer KEY = 1;
				
				final ConcurrentMap<Integer, Integer> map = 
									new TransactionalHashMap<Integer, Integer>();
				
				CountTime.run(new Runnable() {

					public void run() {
						try {
							LockingTransaction.runInTransaction(new Callable<Void>() {
								public Void call() throws Exception {

									map.putIfAbsent(KEY, Integer.valueOf(0));
									
									return null;
								}
							});
						} catch (Exception e) {

							e.printStackTrace();
						}
					}
					
				});
				
				ParallelRunner.run(2, new Runnable() {
					public void run() {
						for (int i=0; i < runCount; i++) {
							try {
								//final int h = i;
								LockingTransaction.runInTransaction(new Callable<Void>() {
									public Void call() throws Exception {
										
										map.replace(KEY, Integer.valueOf(map.get(KEY).intValue() + 1));
										
										return null;
									}
								});
							} catch (Exception e) {

								e.printStackTrace();
							}
							
						}
						
					}});	
			
				Out.println(map.toString());
				
			}
			
			
			
			
		}),
		TRANSACTIONALMAP3(new ParamaterRunnable() {
			public void run(final Object ... parameters) {
				
				final int runCount = (Integer)parameters[0];
				
				//final Integer KEY = 1;
				
				final ConcurrentMap<Integer, Integer> map = 
									new TransactionalHashMap<Integer, Integer>();
					
				
				ParallelRunner.run(2, new Runnable() {
					public void run() {
						for (int i=0; i < runCount; i++) {
							try {
								//final int h = i;
								LockingTransaction.runInTransaction(new Callable<Void>() {
									public Void call() throws Exception {
										
										map.putIfAbsent(map.size(), map.size());
										
										//map.replace(KEY, Integer.valueOf(map.get(KEY).intValue() + 1));
										
										return null;
									}
								});
							} catch (Exception e) {

								e.printStackTrace();
							}
							
						}
						
					}});	
			
				Out.println(map.toString());
				
			}
			
			
			
			
		});
		
		
		
		private final ParamaterRunnable test;
		
		private TEST(ParamaterRunnable test) {

			this.test = test;
		}
		
		public void run(Object ...parameters ) {
			test.run(parameters);
		}
	}
	
	private static interface ParamaterRunnable {
		
		public void run(final Object ... parameters);
	}
	
	
	private static void doCollection(final int runCount, final ConcurrentCollectionTest list) {
		
		ParallelRunner.run(2, new Runnable() {
			public void run() {
				for (int i=0; i < runCount; i++) {
					
					list.addItem();
				}
				
			}
		});		
		
		List<Integer> result = list.getResult();
		
		if (result.size() > 21) {
			System.out.println("List size is " + result.size());
			System.out.println("First element is " + result.get(0));
			System.out.println("Last element is " + result.get(result.size()-1));
		}
		else {
			System.out.println("FINAL VALUE is " + result);	
		}
		
	}
	
	private static void doReadWrite(final int runCount, final CounterTest counter, final boolean doubleCount) {
		
		ParallelRunner.run(
			new Runnable() {
				public void run() {
					for (int i=0; i < runCount; i++) {
						
						StateDebuger.debug(counter.getValue(doubleCount));
					}
				}
			},			
			new Runnable() {
				public void run() {
					for (int i=0; i < runCount; i++) {
						
						StateDebuger.debug(counter.increment(doubleCount));
					}
				}
			}
		);	
	}

	private static void doCounting(final int runCount, final CounterTest counter, final boolean doubleCount) {
		ParallelRunner.run(
			new Runnable() {
				public void run() {
					for (int i=0; i < runCount; i++) {
						
						StateDebuger.debug(counter.increment(doubleCount));
					
					}
					
				}
			},
			new Runnable() {
				public void run() {
					for (int i=0; i < runCount; i++) {
						
						StateDebuger.debug(counter.increment(doubleCount));
					
					}
					
				}
			},
			new Runnable() {
				public void run() {
					for (int i=0; i < runCount * 2; i++) {

						if (counter.getValue(doubleCount).longValue() != 0L) {
							throw new IllegalStateException();
						}
					
					}
					
				}
			});		
		
		System.out.println("FINAL COUNTER VALUE is " + counter.getValue(doubleCount));
	}
	
	public static void main(String[] args) {
				
		if (args == null || args.length == 0) {
			printInstructions();
			return;
		}

		try {
			final TEST test = resolveTest(args);
			final int runCount = resolveRunCount(args);
			final boolean debug = resolveDebugLevel(args);
			
			StateDebuger.setDebugLevel(debug);
			
			CountTime.run(new Runnable() {

				public void run() {
					test.run(runCount);
				}
			});
			
		}
		catch (RuntimeException e) {
			System.out.println(e.getMessage());
			return;
		}
		
	}

	private static boolean resolveDebugLevel(String[] args) {
		boolean debug = false;
		
		if (args.length >= 3) {
			
			if (args[2].toUpperCase().equals("TRUE") ||
				args[2].toUpperCase().equals("FALSE")) {
				debug = Boolean.valueOf(args[2]);
			}
			else {
			
				throw new RuntimeException("Proposed debug parameter is not a boolean.");
				
			}
		}
		return debug;
	}

	private static int resolveRunCount(String[] args) {
		int runCount = 	100;
		
		if (args.length >= 2) {
			
			try {
				runCount = Integer.valueOf(args[1]);
			}
			catch (NumberFormatException e) {
				throw new RuntimeException("Proposed runCount is not a integer.");
			}
		}
		return runCount;
	}

	private static TEST resolveTest(String[] args) {
		TEST testName = null;
		
		try {
			testName = TEST.valueOf(args[0].toUpperCase());
		}
		catch (IllegalArgumentException e) {
			throw new RuntimeException("Unknown testName.");
		}
		return testName;
	}

	private static void printInstructions(String ... errors) { 
		if (errors != null && errors.length > 0) {
			System.out.println();
			for (String error : errors) {
				System.out.println(error);
			}
		}
		
		System.out.println();
		System.out.println("Usage: program {testname} {runCount} {debug}");
		System.out.println();
		System.out.println("Options:");
		System.out.println("  {testname} in { ");
		for (TEST testName : TEST.values()) {
			System.out.println("   " + testName);
		}
		System.out.println("   }");
		System.out.println();
		System.out.println("  {runCount} default 100");
		System.out.println("  {debug} TRUE|FALSE default FALSE");
		System.out.println();
		System.out.println("Example:");
		System.out.println();
		System.out.println("program optimistic 10 true");
		System.out.println();
	}

}
