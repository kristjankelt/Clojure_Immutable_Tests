package me.test.playground;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest10 {

	private static int TEST_SIZE = 100000000;
	
	private final Lock lock = new ReentrantLock();   
	
	private boolean flag = false;
	
	private void test() {
		lock.lock();
		try {
			flag = true;
		}
		finally {
			lock.unlock();
		}
	}
	
	private boolean readFlag() {
		lock.lock();
		try {
			return flag;
		}
		finally {
			lock.unlock();
		}
	}
	
	private boolean readAndWrite() {
		lock.lock();
		try {
			boolean previousFlag = flag;
			flag = true;
			return previousFlag;
		}
		finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		
		final SynchronizedOverheadTest10 test = new SynchronizedOverheadTest10();
		
		CountTime.run(new Runnable() {

			public void run() {
				
				for (int i=0; i < TEST_SIZE; i++) {
					test.test();
				}
			}
			
		});
		
		CountTime.run(new Runnable() {

			public void run() {
				
				for (int i=0; i < TEST_SIZE; i++) {
					@SuppressWarnings("unused")
					boolean value = test.readFlag();
				}
			}
			
		});
		
		CountTime.run(new Runnable() {

			public void run() {
				
				for (int i=0; i < TEST_SIZE; i++) {
					@SuppressWarnings("unused")
					boolean value = test.readAndWrite();
				}
			}
			
		});
		
	}
}
