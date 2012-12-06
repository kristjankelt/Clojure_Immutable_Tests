package me.test.util.debug;


public class StateDebuger {
	
	private static volatile boolean debugLevel = false;
	
	// Making debugLevel volatile should work better than 
	// making this method synchronize (requires synchronized read method
	// to make the code foolproof). Making this only method synchronized should 
	// work (even when considering possible copiler optimizations) but it is not 
	// guarantied to do so in the future.
	public static boolean setDebugLevel(boolean level) {
		debugLevel = level;
		
		return debugLevel;
	}
	
	public static void debug(Object label) {
		if (debugLevel) {
			System.out.println(Thread.currentThread().getName() + " " + label);
		}
	}
	
	public static void debug(String label, int labelPadSize, int statePadSize, Object ... state) {
		if (!debugLevel) {
			return;
		}
		
		Out.padded(label, labelPadSize, statePadSize, state);
	}
}
