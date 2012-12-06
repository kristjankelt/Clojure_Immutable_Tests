package me.test.test;

public final class TestItem implements TestContainer {

	private final String testName;
	private final String groupName;
	private final String setName;
	private final Test test;
	
	public TestItem(final String testName, final String groupName, final String setName, final Test test) {
		super();
		this.testName = testName;
		this.groupName = groupName;
		this.setName = setName;
		this.test = test;
	}

	public String getTestName() {
		return testName;
	}
	
	public String getGroupName() {
		return groupName;
		
	}

	public String getSetName() {
		return setName;
	}
	
	public Test test() {
		return test;		
	}
}
