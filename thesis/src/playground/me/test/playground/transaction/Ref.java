package me.test.playground.transaction;

import java.util.Iterator;

public class Ref {
	
	

	private final int id;
	
	public Ref(int id) {
		this.id = id;
	}
	
	public String toString() {
		return Integer.toString(id);
	}
	
	public synchronized boolean tryCommit(Iterator<Ref> iterator) {
		
		
		
		return false;
	}
}
