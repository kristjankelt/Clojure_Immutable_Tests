package me.test.test.lowlevel;


public interface LowLevelTest {

	public String getGroupName();
	
	public void prepare1(int testSize);
	
	public void prepare2(int testSize);
	
	public void test1(int testSize);
	
	public void test2(int testSize);
}
 