package me.test.util.debug;

import me.test.util.StringUtil;

public final class CountTime {

	public static long run(final Runnable runnable) {
		return run((String)null, runnable, true);
	}
	
	public static long run(final Runnable runnable, final boolean showElapsedTime) {
		return run((String)null, runnable, showElapsedTime);
	}
	
	public static long run(
						final String label, final Runnable runnable, 
						final boolean showElapsedTime) {
		
		
		return run((Integer)null, label, runnable, showElapsedTime);
	}
	
	public static long run(final Integer labelPadSize, 
							final String label, 
							final Runnable runnable, 
							boolean showElapsedTime) {
		
		long startTime = System.nanoTime();
		
		runnable.run();
		
		long elapsedTime = System.nanoTime() - startTime;
		
		
		if (showElapsedTime) {
			
			if (labelPadSize == null) {
				System.out.println(
						(label == null ? "" : label + "; ") + 
						"Elapsed time: " + (elapsedTime / 1000000.0) + " milliseconds.");
			}
			else {
				
				System.out.println(
						(label == null ? "" : 
							StringUtil.pad(label, labelPadSize.intValue(), " |"))
						 +
						StringUtil.pad(
								Double.toString(elapsedTime / 1000000.0) + " milliseconds",
									22,
										" |"));
			}
		}
		
		return elapsedTime;
	}
}
