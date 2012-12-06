package me.test.playground;

import me.test.util.debug.CountTime;

public class CopyOnWrite1 {
	
	private static int TEST_SIZE = 1000000000;
	private static int TAIL_SIZE = 32;
	
	private Object[] buf;
	private int index;
	
	public CopyOnWrite1() {
		buf = new Object[0];
		index = -1;
	}
	
	private void copyOnWrite() {
		index++;
		
		if (buf.length >= TAIL_SIZE) {
			buf = new Object[0];
		}
		
		Object[] newBuf = new Object[buf.length + 1];
		System.arraycopy(buf, 0, newBuf, 0, buf.length);
		newBuf[newBuf.length - 1] = index;
		buf = newBuf;
		
	}

	public static void main(String[] args) {
		
		final CopyOnWrite1 test = new CopyOnWrite1();
		
		CountTime.run(new Runnable() {

			public void run() {
				
				for (int i=0; i < TEST_SIZE; i++) {
					test.copyOnWrite();
				}
			}
			
		});
		
	}
}
