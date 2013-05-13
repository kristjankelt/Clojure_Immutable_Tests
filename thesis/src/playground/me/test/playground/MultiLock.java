package me.test.playground;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import me.test.util.concurrent.ParallelRunner;
import me.test.util.debug.CountTime;

public class MultiLock {

	private static int TEST_SIZE = 10000;
	
	//private static int THREADS = 4;
	
	private static AtomicInteger locks = new AtomicInteger(0);
	
	private static Lock lock = new ReentrantLock();
	
	private static volatile int[] table = new int[31];
	
	private static AtomicIntegerArray table2 = new AtomicIntegerArray(31);
	
	private static int mask(int ... indexes) {
		int mask = 0;
		
		for (int index : indexes) {
			mask = mask | 1 << index;
		}

		return mask;
	}
	
	private static void takeANap() {
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		


		CountTime.run(new Runnable() {
			
			public void run() {
				
				ParallelRunner.run(new Runnable() {
			
					public void run() {
						int[] items = new int[] {8, 9, 10, 11};
						int mask = mask(items);
						
						for (int i=0; i < TEST_SIZE; i++) {
							requestLock(mask, 1);
							takeANap();

							for (int k : items) {
								int value = table2.get(k);
								table2.set(k, value + 1);
							}
			
							releaseLock(mask, 1);
						}
					}
				}, new Runnable() {
			
					public void run() {
						int[] items = new int[] {8, 9, 10, 11};
						int mask = mask(items);
						
						for (int i=0; i < TEST_SIZE; i++) {
							requestLock(mask, 2);
							takeANap();

							for (int k : items) {
								int value = table2.get(k);
								table2.set(k, value + 1);
							}
							

							releaseLock(mask, 2);
						}
					}
				}, new Runnable() {
			
					public void run() {
						int[] items = new int[] {8, 9, 10, 11};
						int mask = mask(items);
						
						for (int i=0; i < TEST_SIZE; i++) {
							
							
							requestLock(mask, 3);
							takeANap();
							
							for (int k : items) {
								int value = table2.get(k);
								table2.set(k, value + 1);
							}
							
							releaseLock(mask, 3);
							
						}
					}
				}, new Runnable() {
			
					public void run() {
						int[] items = new int[] {8, 9, 10, 11};
						int mask = mask(items);
						
						for (int i=0; i < TEST_SIZE; i++) {

							requestLock(mask, 4);
							
							takeANap();
							
							for (int k : items) {
								int value = table2.get(k);
								table2.set(k, value + 1);
							}
							
							
							releaseLock(mask, 4);
						}
					}
				});
			}
		});
		
		long total2 = 0;
		for (int i = 0; i < table2.length(); i++) {
			total2 += table2.get(i);
		}
		System.out.println(total2);
		
		//////////////////////////////
		
table = new int[31];
		
		CountTime.run(new Runnable() {
			
			public void run() {
				
				ParallelRunner.run(new Runnable() {
					
					public void run() {
						int[] items = new int[] {0, 1, 2, 3};
						for (int i=0; i < TEST_SIZE; i++) {
							synchronized (table) {
								takeANap();
								for (int k : items) {
									table[k]++;
								}
							}
						}
					}
				}, new Runnable() {
					
					public void run() {
						int[] items = new int[] {4, 5, 6, 7};
						for (int i=0; i < TEST_SIZE; i++) {
							synchronized (table) {
								takeANap();
								for (int k : items) {
									table[k]++;
								}
							}
						}
					}
				},  new Runnable() {
					
					public void run() {
						int[] items = new int[] {8, 9, 10, 11};
						for (int i=0; i < TEST_SIZE; i++) {
							synchronized (table) {
								takeANap();
								for (int k : items) {
									table[k]++;
								}
							}
						}
					}
				},  new Runnable() {
					
					public void run() {
						int[] items = new int[] {8, 9, 10, 11};
						for (int i=0; i < TEST_SIZE; i++) {
							synchronized (table) {
								takeANap();
								for (int k : items) {
									table[k]++;
								}
							}
						}
					}
				});
			}
		});
		
		long total1 = 0;
		for (int i : table) {
			total1 += i;
		}
		System.out.println(total1);
		
		////////////////////////////
		
		table = new int[31];

		CountTime.run(new Runnable() {
			
			public void run() {
				
				ParallelRunner.run(new Runnable() {
			
					public void run() {
						int[] items = new int[] {0, 1, 2, 3};
						
						for (int i=0; i < TEST_SIZE; i++) {
							lock.lock();
							try {
								takeANap();
								for (int k : items) {
									table[k]++;
								}
							}
							finally {
								lock.unlock();
							}
						}
					}
				}, new Runnable() {
			
					public void run() {
						int[] items = new int[] {4, 5, 6, 7};
						
						for (int i=0; i < TEST_SIZE; i++) {
							lock.lock();
							try {
								takeANap();
								for (int k : items) {
									table[k]++;
								}
							}
							finally {
								lock.unlock();
							}
						}
					}
				}, new Runnable() {
			
					public void run() {
						int[] items = new int[] {8, 9, 10, 11};
						
						for (int i=0; i < TEST_SIZE; i++) {
							lock.lock();
							try {
								takeANap();
								for (int k : items) {
									table[k]++;
								}
							}
							finally {
								lock.unlock();
							}
						}
					}
				}, new Runnable() {
			
					public void run() {
						int[] items = new int[] {8, 9, 10, 11};
						
						for (int i=0; i < TEST_SIZE; i++) {
							lock.lock();
							try {
								takeANap();
								for (int k : items) {
									table[k]++;
								}
							}
							finally {
								lock.unlock();
							}
						}
					}
				});
			}
		});
		
		long total3 = 0;
		for (int i : table) {
			total3 += i;
		}
		System.out.println(total3);
		
	}
		
	
	private static int takeLockMask(int mask, int status) {
		return mask | status;
	}
	
	private static int releaseLockMask(int mask, int status) {
		return ~mask & status;
	}
	
	private static void report(String label, int mask, int id) {
		
		System.out.println(label + " " + 
					Integer.toBinaryString(mask) + " - " + id);
		
	}

	private static void requestLock(int mask, int id) {
		
		int locksStatus = 0;
		int tries = 0;
		int count = 0;
		
		do {
			locksStatus = locks.get();
			
			boolean isFree = (mask & ~locksStatus) == mask;
			
			if (!isFree) {
				tries++;
				continue;
			}
			
			if (locks.compareAndSet(
					locksStatus, takeLockMask(mask, locksStatus)))  {
				
				break;	
			}
		} while (true);
		
	}
	
	private static void releaseLock(int mask, int id) {
		int locksStatus = 0;
		int count = 0;
		
		do {
			
			locksStatus = locks.get();
		
		} while (!locks.compareAndSet(
				locksStatus, releaseLockMask(mask, locksStatus)));
		
		
	}
	
}
