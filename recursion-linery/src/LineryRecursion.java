
public class LineryRecursion {

	public static long pow(int value, int power) {
		return powR(value, power, 0);
	}

	private static long powR(int value, int power, int current) {
		if (power == 0) { return 1; }
		if (power == 1) { return current; }
		if (current == 0) { current = value; }
		return powR(value, power - 1, multiply(current, value));
	}

	public static int multiply(int value, int count) {
		if (count == 1) { return value; }
		return value + multiply(value, count - 1);
	}

	public static int sum(int ar[]) {
		return sumR(0, ar); 
	}

	private static int sumR(int index, int[] ar) {
		if (index >= ar.length) { return 0; }
		return ar[index] + sumR(index + 1, ar);
	}

	public static int square(int x) {
		if (x == 0) { return 0; }
		if (x < 0) { x = 0 - x; }
		return square(x - 1) + x + x - 1; 

		// Но вообще без умножения было бы проще решить 
		// эту задачу через деление, а не рекурсию
		// return (int) (x / (1.0 / x));
	}

	public static boolean isSubstring(String str, String substr) {
		if (str.length() < substr.length()) { return false; }
		if (str.length() == substr.length()) { return isEqual(str, substr); } 
		return isSubstring(str.substring(1), substr); 
	}

	public static boolean isEqual(String str, String substr) {
		if (str.charAt(0) == substr.charAt(0)) {
			if (str.length() == 1) { return true; }
			return isEqual(str.substring(1), substr.substring(1));                
		} else {
			// Здесь не могу пройти только один тест, где символы в середине слова:
			// assertTrue(LineryRecursion.isSubstring("Hello", "el"));
			return false;
		}
	}

}
