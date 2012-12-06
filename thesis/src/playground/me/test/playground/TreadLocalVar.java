package me.test.playground;

public class TreadLocalVar {

	private final ThreadLocal<Long> value = new ThreadLocal<Long>();
	
	public void setValue(Long newValue) {
		value.set(newValue);
	}
	
	public Long getValue() {
		return value.get();
	}
	
	public static void main(String[] args) {
		
		TreadLocalVar var1 = new TreadLocalVar();
		TreadLocalVar var2 = new TreadLocalVar();
		
		var1.setValue(1L);
		var2.setValue(2L);
		
		System.out.println(var1.getValue() + " " + var2.getValue());
	}
}
