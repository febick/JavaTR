public class Main {

	static long result = 0;

	public static void main(String[] args) {

		// Получаем семь случайных чисел и записываем их в long
		for (int i = 1; i < 8; i++) {
			byte newNumber = getNewRandomNumber();
			
			if (i == 1) {
				result = result | newNumber;
			} else {
				result = (result << 8) | newNumber;
			} 
			
		}
		
		// Выводим результат
		System.out.println("Выигрышная комбинация:");
		System.out.println((byte) (result & 0xFF));
		System.out.println((byte) (result >> 8));
		System.out.println((byte) (result >> 16));
		System.out.println((byte) (result >> 24));
		System.out.println((byte) (result >> 32));
		System.out.println((byte) (result >> 40));
		System.out.println((byte) (result >> 48));
		
		
	}
	
	private static byte getNewRandomNumber() {
		
		byte result = (byte) (Math.random() * 49);
		
		while (checkIfNumberExist(result)){
		    // Пробуем получить новое число
			result = (byte) (Math.random() * 49);
		}
		
		return result;
	}
	
	private static boolean checkIfNumberExist(byte value) {
		
		if (value == 0) {
			return true;
		} else if (value == (byte) (result & 0xFF)) {
			return true;
		} else if (value == (byte) (result >> 8)) {
			return true;
		} else if (value == (byte) (result >> 16)) {
			return true;
		} else if (value == (byte) (result >> 24)) {
			return true;
		} else if (value == (byte) (result >> 32)) {
			return true;
		} else if (value == (byte) (result >> 40)) {
			return true;
		} else if (value == (byte) (result >> 48)) {
			return true;
		} else {
			return false;
		}

	}

}
