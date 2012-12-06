package me.test.playground;

import me.test.util.debug.CountTime;

public class CopyOnWrite3 {
	
	private static int TEST_SIZE = 1000000000;
	private static int TAIL_SIZE = 4;
	private static int BUF_SIZE = 8;
	
	private Object[][] buf;
	private int index;
	private int dataIndex;
	
	public CopyOnWrite3() {
		buf = new Object[BUF_SIZE][];
		index = -1;
		dataIndex = 0;
	}
	
	private void copyOnWrite() {
		index++;
		
		if (dataIndex >= BUF_SIZE) {
			buf = new Object[BUF_SIZE][];
			dataIndex = 0;
		}
		
		if (buf[dataIndex] == null) {
			buf[dataIndex] = new Object[] {index};
		}
		else {
			Object[] newBuf = new Object[buf[dataIndex].length + 1];
			System.arraycopy(buf[dataIndex], 0, newBuf, 0, buf[dataIndex].length);
			newBuf[newBuf.length - 1] = index;
			buf[dataIndex] = newBuf;
			
			if (buf[dataIndex].length == TAIL_SIZE) {
				dataIndex++;
			}
		}
	
	}

	public static void main(String[] args) {
		
		final CopyOnWrite3 test = new CopyOnWrite3();
		
		CountTime.run(new Runnable() {

			public void run() {
				
				for (int i=0; i < TEST_SIZE; i++) {
					test.copyOnWrite();
				}
			}
			
		});
		
	}
}
