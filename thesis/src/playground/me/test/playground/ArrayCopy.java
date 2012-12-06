package me.test.playground;

import java.util.Arrays;

public class ArrayCopy {

	public static void main(String[] args) {
		
		Object[][] data =  {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}};
		
		Object[][] copy = new Object[3][3];
		System.arraycopy(data, 0, copy, 0, 3);
		
		Object[][] copy2 = data.clone();
		
		
		data[1][1] = 8;
		
		
		for (Object[] e : data) {
			System.out.println(Arrays.toString(e));
		}
		
		System.out.println();
		
		for (Object e : copy) {
			System.out.println(Arrays.toString((Object[])e));
		}
		
		System.out.println();
		
		for (Object e : copy2) {
			System.out.println(Arrays.toString((Object[])e));
		}
		
	}
}
