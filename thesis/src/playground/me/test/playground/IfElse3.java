package me.test.playground;

import me.test.util.debug.CountTime;

public class IfElse3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CountTime.run(new Runnable() {

			public void run() {
				for (int i=0; i < 10000000; i++) {
					@SuppressWarnings("unused")
					boolean a = ifElse();
				}
			}

			
		});
	}
	

	private static boolean ifElse() {
		if (thisIsTrue()) {
			return false;
		}
		return true;
	}
	
	public static boolean thisIsTrue() {
		return true;
	}

}
