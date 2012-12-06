package me.test.test.collection.ver1;

import java.util.ArrayList;
import java.util.List;

import me.test.test.Test;

public final class ArrayListInitialCapacityTest extends AbstractArrayListTest  {

	@Override
	Test sequentalFill() {
		return new Test() {
			
			public void prepare(int testCount) {}

			public void run(int testCount) {
				
				List<Integer> list = new ArrayList<Integer>(testCount);
				
				for (int i = 0; i < testCount; i++) {
				
					list.add(Integer.valueOf(0));
				}
			}
			
		};
	}

	@Override
	protected String sequentalFillName() {
		return super.sequentalFillName() + "#initialCapacity";
	}
	
	@Override
	protected Test sequentalRead() {
		return null;
	}

	@Override
	protected Test reverseSequentalRead() {
		return null;
	}

	@Override
	protected Test randomRead() {
		return null;
	}

	@Override
	protected Test computeSize() {
		return null;
	}

	@Override
	protected Test createCopy() {
		return null;
	}

	
}
