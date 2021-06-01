public class Main {

	static long result = 0;

	public static void main(String[] args) {

		for (int i = 0; i < 7; i++) {
			byte newNumber = (byte) (1 + Math.random() * 49);

			if (i == 1) {
				result = result | newNumber;
			} else {
				
				while (checkIfNumberExist(newNumber, i)){
					newNumber = (byte) (1 + Math.random() * 49);
				}
				
				result = (result << 8) | newNumber;
			} 
			
			System.out.println(newNumber);
			
		}
		
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
