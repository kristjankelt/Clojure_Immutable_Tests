package me.test.test;

public interface TestContainer {

	public String getTestName();
	
	public String getGroupName();
	
	public String getSetName();
	
	public Test test();
}
