package me.test.playground;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import me.test.util.concurrent.ParallelRunner;
import me.test.util.debug.CountTime;

public class MultiLock {

	private static int TEST_SIZE = 1000;
	
	//private static int THREADS = 4;
	
	private static AtomicInteger locks = new AtomicInteger(0);
	
	private static Lock lock = new ReentrantLock();
	
	private static int[] table = new int[31];
	
	private static volatile boolean flag = false;
	
	private static int mask(int ... indexes) {
		int mask = 0;
		
		for (int index : indexes) {
			mask = mask | 1 << index;
		}

		return mask;
	}
	
	private static void takeANap() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		
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
		
		table = new int[31];

		CountTime.run(new Runnable() {
			
			public void run() {
				
				ParallelRunner.run(new Runnable() {
			
					public void run() {
						int[] items = new int[] {0, 1, 2, 3};
						int mask = mask(items);
						
						for (int i=0; i < TEST_SIZE; i++) {
							requestLock(mask);
							takeANap();
							for (int k : items) {
								table[k]++;
							}
							releaseLock(mask);
						}
					}
				}, new Runnable() {
			
					public void run() {
						int[] items = new int[] {4, 5, 6, 7};
						int mask = mask(items);
						
						for (int i=0; i < TEST_SIZE; i++) {
							requestLock(mask);
							takeANap();
							for (int k : items) {
								table[k]++;
							}
							releaseLock(mask);
						}
					}
				}, new Runnable() {
			
					public void run() {
						int[] items = new int[] {8, 9, 10, 11};
						int mask = mask(items);
						
						for (int i=0; i < TEST_SIZE; i++) {
							requestLock(mask);
							takeANap();
							for (int k : items) {
								table[k]++;
							}
							releaseLock(mask);
						}
					}
				}, new Runnable() {
			
					public void run() {
						int[] items = new int[] {8, 9, 10, 11};
						int mask = mask(items);
						
						for (int i=0; i < TEST_SIZE; i++) {
							requestLock(mask);
							takeANap();
							for (int k : items) {
								table[k]++;
							}
							releaseLock(mask);
						}
					}
				});
			}
		});
		
		long total2 = 0;
		for (int i : table) {
			total2 += i;
		}
		System.out.println(total2);
		
		//////////////////////////////////////////////////
		
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
	
	private static int takeLock(int mask, int status) {
		return mask | status;
	}
	
	private static int releaseLock(int mask, int status) {
		return ~mask & status;
	}

	private static void requestLock(int mask) {
		int locksStatus = 0;
		int tries = 0;
		
		do {
			locksStatus = locks.get();
		
			boolean isFree = (mask & ~locksStatus) == mask;
			
			if (!isFree) {
				tries++;
				continue;
			}
			
		} while (!locks.compareAndSet(
				locksStatus, takeLock(mask, locksStatus)));
		
		flag = true;
	}
	
	private static void releaseLock(int mask) {
		int locksStatus = 0;
		
		do {
			locksStatus = locks.get();
		
			boolean isFree = (mask & ~locksStatus) == mask;
			
			if (!isFree) {
				continue;
			}
		
		} while (!locks.compareAndSet(
				locksStatus, releaseLock(mask, locksStatus)));
		
		flag = false;
	}
	
}
