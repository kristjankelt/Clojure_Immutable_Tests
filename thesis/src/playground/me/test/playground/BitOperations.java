package me.test.playground;


public class BitOperations {
	
	public static void main(String[] args) {
		
		int mask =  Integer.parseInt("00101000", 2);
		
		int value = Integer.parseInt("10000011", 2);
		
		table("TEST",
				mask,
				value,
				 ~value,
				(mask &  ~value)
		);
		
		System.out.println((mask & ~value) == mask);
		
		System.out.println(Integer.toBinaryString(mask | value));
		
		System.out.println(Integer.toBinaryString(mask(3)));
		
		
		System.out.println(Integer.toBinaryString(mask(0, 1, 2, 3, 4)));
		
		System.out.println(Integer.toBinaryString(
				~mask(0, 1, 2, 4) & mask(0, 1, 2, 3, 4)
		));
		
	}
	
	private static int mask(int ... indexes) {
		int mask = 0;
		
		for (int index : indexes) {
			mask = mask | 1 << index;
		}

		return mask;
	}
	
	public static void table(String label, int ... operations) {
		
		System.out.println(label);
		for (int i : operations) {
			System.out.println(Integer.toBinaryString(i));
		}
		System.out.println();
		
	}
}
