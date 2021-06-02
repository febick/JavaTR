public class Main {

	private static final int C_COUNT = 7;
	private static final double M_MIN_NUMBER = 1;
	private static final double M_MAX_NUMBER = 49;
	
	private static long result = 0;

	public static void main(String[] args) {
			showLotto();
	}
	
	private static void showLotto() {
		for (int i = 0; i < C_COUNT; i++) {
			byte newNumber = getRandomNumber();

			if (i == 1) {
				result = result | newNumber;
			} else {
				
				while (checkIfNumberExist(newNumber, i)){
					newNumber = getRandomNumber();
				}
				
				result = (result << 8) | newNumber;
			} 
			
			System.out.print(newNumber + "; ");
			
		}
	}
	
	private static byte getRandomNumber() {
		return (byte) (M_MIN_NUMBER + Math.random() * M_MAX_NUMBER);
	}
	
	private static boolean checkIfNumberExist(byte value, int iteration) {
		
		var status = false;
		
		if (iteration != 0) {
			for (int i = 0; i < 7; i++) {
				if (value == result >> iteration * 8) {
					status = false;
				}
			}
		}
		
		return status;
	}

}
