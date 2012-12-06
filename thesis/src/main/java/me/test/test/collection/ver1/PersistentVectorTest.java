package me.test.test.collection.ver1;


import me.test.test.Test;
import clojure.lang.PersistentVector;

public final class PersistentVectorTest extends AbstractPersitentVectorTest  {

	@Override
	Test sequentalFill() {
		return new Test() {
			
			public void prepare(int testCount) {}

			public void run(int testCount) {
				PersistentVector list = PersistentVector.create();

				for (int i = 0; i < testCount; i++) {
				
					list = list.cons(Integer.valueOf(0));
				}
			}
			
		};
	}
	
}
