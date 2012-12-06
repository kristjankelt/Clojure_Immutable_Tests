package me.test.playground;

import me.test.util.debug.CountTime;

public class CopyOnWrite2 {
	
	private static int TEST_SIZE = 1000000000;
	private static int TAIL_SIZE = 8;
	private static int BUF_SIZE = 4;
	
	private Object[] buf;
	private Object[][] buf2;
	private int index;
//	private Object[][] finalBuf;
	private int dataIndex;
	
	public CopyOnWrite2() {
		buf = new Object[0];
		buf2 = new Object[0][];
//		finalBuf = new Object[1][];
		index = -1;
		dataIndex = -1;
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
		
		if (buf.length == TAIL_SIZE) {
			dataIndex++;
			
			Object[][] newBuf2 = new Object[dataIndex + 1][];
			System.arraycopy(buf2, 0, newBuf2, 0, dataIndex);
//			if (dataIndex > 0) newBuf2[0] = buf2[0];
//			if (dataIndex > 1) newBuf2[1] = buf2[1];
//			if (dataIndex > 2) newBuf2[2] = buf2[2];
			newBuf2[dataIndex] = buf;
			buf2 = newBuf2;
			
			if (dataIndex == BUF_SIZE - 1) {
//				Object[] newFinalBuf = new Object[4 * TAIL_SIZE];
//				System.arraycopy(buf2[0], 0, newFinalBuf, 0, TAIL_SIZE);
//				System.arraycopy(buf2[1], 0, newFinalBuf, 7, TAIL_SIZE);
//				System.arraycopy(buf2[2], 0, newFinalBuf, 15, TAIL_SIZE);
//				System.arraycopy(buf2[3], 0, newFinalBuf, 23, TAIL_SIZE);
//				
//				finalBuf[0] = newFinalBuf;
				buf2 = new Object[0][];

				dataIndex = -1;
			}
		}
	}

	public static void main(String[] args) {
		
		final CopyOnWrite2 test = new CopyOnWrite2();
		
		CountTime.run(new Runnable() {

			public void run() {
				
				for (int i=0; i < TEST_SIZE; i++) {
					test.copyOnWrite();
				}
			}
			
		});
		
	}
}
