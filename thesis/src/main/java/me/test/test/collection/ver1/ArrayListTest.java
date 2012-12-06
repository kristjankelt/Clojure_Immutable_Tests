package me.test.test.collection.ver1;

import java.util.ArrayList;
import java.util.List;

import me.test.test.Test;

public final class ArrayListTest extends AbstractArrayListTest  {

	@Override
	Test sequentalFill() {
		return new Test() {
			
			public void prepare(int testCount) {}

			public void run(int testCount) {
				
				List<Integer> list = new ArrayList<Integer>();
				
				for (int i = 0; i < testCount; i++) {
				
					list.add(Integer.valueOf(0));
				}
			}
			
		};
	}
	
}
