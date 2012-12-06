package me.test.util;

public final class StringUtil {

	public static <E> String join (final String delimiter, final E ... parts) {
	    
		final StringBuilder result = new StringBuilder();
		
	    for (int i = 0; i < parts.length -1; i++) {
	      result.append(parts[i]);
	      
	      result.append(delimiter);
	    }
	    
	    result.append(parts[parts.length-1]);
	    
	    return result.toString();
	  }

	public static String pad(String label, int labelPadSize, String tail) {
		
		StringBuilder builder = new StringBuilder();
		
		if (label.length() <= labelPadSize) {
			builder.append(
						String.format("%1$-" + labelPadSize + "s", label));
			builder.append(tail);
		}
		else {
			builder.append(
					label.substring(0, labelPadSize-3));
			
			builder.append("...");
			builder.append(tail);
		}
		
		return builder.toString();
	}
}
