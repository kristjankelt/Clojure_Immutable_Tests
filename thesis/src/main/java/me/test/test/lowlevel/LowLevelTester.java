package me.test.test.lowlevel;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import me.test.test.Test;
import me.test.test.TestContainer;
import me.test.test.TestItem;

public class LowLevelTester {

	public Set<TestContainer> getTests(final Class<? extends LowLevelTest> testClass ) {
		try {
			return Collections.unmodifiableSet(new HashSet<TestContainer>() {
				
				private static final long serialVersionUID = 1L;

				{
					LowLevelTest test = testClass.newInstance();
					
					add(new TestItem("lowLevelTest1", test.getGroupName(), "lowlevel", lowLevelTest1(test)));
					add(new TestItem("lowLevelTest2", test.getGroupName(), "lowlevel", lowLevelTest2(test)));
				}

				
			});
		
		} 
		catch (InstantiationException e) {
			throw new RuntimeException(e);
		} 
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Test lowLevelTest1(final LowLevelTest test) {
		
		return new Test() {

			public void prepare(int testSize) {
				test.prepare1(testSize);
			}

			public void run(int testSize) {
				
				test.test1(testSize);
			}
		};
	}
	
	private Test lowLevelTest2(final LowLevelTest test) {
		
		return new Test() {

			public void prepare(int testSize) {
				test.prepare2(testSize);
			}

			public void run(int testSize) {
				
				test.test2(testSize);
			}
		};
	}
	
}
