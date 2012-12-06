package me.test.test.locks;

import java.util.Iterator;

public interface LockTest {

	public String getGroupName();
	
	public LockTest createInstance(int id);
	
	public boolean tryCommit(Iterator <LockTest> iterator);
	
	public void lockAndRelease();
	
	public void lock();
	
	public void release();
}
 