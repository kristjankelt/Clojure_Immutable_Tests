package me.test.test.collection.ver1;

import me.test.test.Test;
import clojure.lang.IPersistentCollection;
import clojure.lang.ITransientVector;
import clojure.lang.PersistentVector;

public final class TransientPersistentVectorTest extends AbstractPersitentVectorTest  {

	@Override
	Test sequentalFill() {
		return new Test() {
			
			public void prepare(int testCount) {}

			public void run(int testCount) {
				ITransientVector list = PersistentVector.EMPTY.asTransient();
				

				for (int i = 0; i < testCount; i++) {
					list = (ITransientVector) list.conj(Integer.valueOf(0));
				}
				
				@SuppressWarnings("unused")
				IPersistentCollection persistentList = list.persistent();
			}
			
		};
	}

	@Override
	protected String sequentalFillName() {
		return super.sequentalFillName() + "#Transient";
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
