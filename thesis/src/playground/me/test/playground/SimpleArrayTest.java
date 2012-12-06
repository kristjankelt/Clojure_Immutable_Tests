package me.test.playground;

public class SimpleArrayTest {

	private static int[][] createMatrix(int n, int m) {
		
		int[][] a = new int[n][m];
		
		return a;
	}
		
	public static void main(String[] args) {
		
		int[][] a = createMatrix(1000, 1000);
		
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				a[i][j] = 1;
			}
		}
	}

}
