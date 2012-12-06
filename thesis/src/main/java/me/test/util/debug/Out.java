package me.test.util.debug;

import me.test.util.StringUtil;

public final class Out {

	public static void separator() {
		System.out.println("--------------------");
	}
	
	public static void csv(String label, Object ... state) {
		csv(",", label, state);
	}
	
	private static void csv(String separator, String label, Object ... state) {
		if (label == null) {
			throw new NullPointerException("Label is null");
		}
		
		StringBuilder debugStr = new StringBuilder(label);
		
		if (state != null && state.length > 0) {
			
			for (Object stateObj : state) {
				
				debugStr.append(separator);
				debugStr.append(stateObj == null ? "-" : stateObj.toString());
			}
		}
		
		System.out.println(debugStr.toString());
	}
	
	public static void padded(String label, int labelPadSize, int statePadSize, String separator, Object ... state) { 
		if (label == null) {
			throw new NullPointerException("Label is null");
		}
		if (labelPadSize < 5) {
			throw new IllegalArgumentException("Wrong label pad size.");
		}
		if (statePadSize < 5) {
			throw new IllegalArgumentException("Wrong state pad size.");
		}
		
		StringBuilder debugStr = new StringBuilder(Thread.currentThread().getName());
		
		debugStr.append(separator);
		
		debugStr.append(StringUtil.pad(label, labelPadSize, separator));
		
		if (state != null && state.length > 0) {
			
			for (Object stateObj : state) {
				
				debugStr.append(StringUtil.pad(
						stateObj == null ? "-" : stateObj.toString(), 
						statePadSize, separator));
				
			}
		}
		
		System.out.println(debugStr.toString());
	}
		
	
	public static void padded(String label, int labelPadSize, int statePadSize, Object ... state) {
		padded(label, labelPadSize, statePadSize, " |", state);
	}
	
	public static void println(final Object line) {
		System.out.println(line);
	}

}
