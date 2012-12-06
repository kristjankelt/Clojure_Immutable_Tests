package me.test.test.collection.ver1;

import me.test.test.Test;

import com.google.common.collect.ImmutableList;




public final class ImmutableListTest extends AbstractImmutableListTest  {
	
	@Override
	protected Test sequentalFill() {
		return new Test() {
			
			public void prepare(int testCount) {}
	
			public void run(int testCount) {
				ImmutableList<Integer> list = ImmutableList.of();
				
				for (int i = 0; i < testCount; i++) {
				
					list = ImmutableList.<Integer>builder().addAll(list).add(Integer.valueOf(0)).build();
				}
			}
			
		};
	}
}
