package me.test.playground.package1;

import java.util.ArrayList;
import java.util.List;

public class ImmutableList<E> {
	private final List<E> list;
	
	public ImmutableList() {
		list = new ArrayList<E>();
	}
	
	protected void add(E item) {
		list.add(item);
	}
	
	public String toString() {
		return list.toString();
	}
}
