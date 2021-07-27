package telran.utils;

public class MemoryService {

	private static int lastMinValue;
	private static int lastMaxValue;

	public static int getMaxAvailableMemory() {
		lastMinValue = 0;
		lastMaxValue = Integer.MAX_VALUE;
		var currentValue = lastMaxValue;
		var diff = lastMaxValue - lastMinValue;
		while (diff > 1) {
			diff = lastMaxValue - lastMinValue;
			try {
				@SuppressWarnings("unused")
				byte ar[] = new byte[currentValue];
				ar = null;
				lastMinValue = currentValue;
			} catch (OutOfMemoryError e) {
				lastMaxValue = currentValue;
			} 
			currentValue = getMiddle(lastMinValue, lastMaxValue);
		}

		System.out.println("EXIT: Maximum available memory: " + lastMaxValue + " byte(s).");
		return lastMaxValue - 1;
	}

	
	private static int getMiddle(int from, int to) {
		if (to == from + 1 || to == from) { return to; }
		var difference = (lastMaxValue - lastMinValue) / 2;
		return lastMinValue + difference;
	}

}
