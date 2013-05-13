package me.test.playground;

import java.util.ArrayList;

import me.test.util.debug.CountTime;

public class SynchronizedOverheadTest18 {

	private static int TEST_SIZE = 10000000;
	
	private static int CASCADE_SIZE = 10;
	
	@SuppressWarnings("serial")
	private final static ArrayList<Integer> locks = new ArrayList<Integer>() {
		
		{
			for (int i=0; i < CASCADE_SIZE; i++) {
				add(new Integer(i));
			}
		}
	};

	private boolean flag;
	
	private void cascadeLock(int level) {
		synchronized (locks.get(level)) {
			if (level > 0) {
				cascadeLock(level - 1);
			}
			else {
				flag = true;
			}
		}
	}
	
	
	private void test() {
		cascadeLock(CASCADE_SIZE - 1);
	}
	
	protected boolean readFlag() {
		return flag;
	}
	
	public static void main(String[] args) {
		
		final SynchronizedOverheadTest18 test = new SynchronizedOverheadTest18();
		
		CountTime.run(new Runnable() {

			public void run() {
				
				for (int i=0; i < TEST_SIZE; i++) {
					test.test();
				}
			}
			
		});
		

	}
}
