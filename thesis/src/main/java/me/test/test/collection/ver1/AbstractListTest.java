package me.test.test.collection.ver1;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import me.test.test.Test;
import me.test.test.TestContainer;
import me.test.test.TestItem;

public abstract class AbstractListTest {

	abstract String groupName();
	
	abstract Test sequentalFill();
	
	abstract Test sequentalRead();
	
	abstract Test reverseSequentalRead();
		
	abstract Test randomRead();
	
	abstract Test computeSize();
	
	abstract Test createCopy();
	
	public Set<TestContainer> getTests() {
		
		return Collections.unmodifiableSet(new HashSet<TestContainer>() {
			private static final long serialVersionUID = 1L; 
			{
				final Test sequentalFill = sequentalFill();
				
				if (sequentalFill != null) {
					add(new TestItem(sequentalFillName(), groupName(), "collection2", sequentalFill()));
				}
//				
//				final Test sequentalRead = sequentalRead();
//				
//				if (sequentalRead != null) {
//					add(new TestItem(sequentalReadName(), groupName(), sequentalRead()));
//				}
//				
//				final Test reverseSequentalRead = reverseSequentalRead();
//				
//				if (reverseSequentalRead != null) {
//					add(new TestItem(reverseSequentalReadName(), groupName(), reverseSequentalRead()));
//				}
//				
//				final Test randomRead = randomRead();
//				
//				if (randomRead != null) {
//					add(new TestItem(randomReadName(), groupName(), randomRead()));
//				}
//				
//				final Test computeSize = computeSize();
//				
//				if (computeSize != null) {
//					add(new TestItem(computeSizeName(), groupName(), computeSize()));
//				}
//				
//				final Test createCopy = createCopy();
//				
//				if (createCopy != null) {
//					add(new TestItem(createCopyName(), groupName(), createCopy()));
//				}
			
			}
		});
	}

	protected String computeSizeName() {
		return "computeSize";
	}

	protected String randomReadName() {
		return "randomRead";
	}

	protected String reverseSequentalReadName() {
		return "reverseSequentalRead";
	}

	protected String sequentalReadName() {
		return "sequentalRead";
	}

	protected String sequentalFillName() {
		return "sequentalFill";
	}
	
	protected String createCopyName() {
		return "createCopy";
	}

	
}
