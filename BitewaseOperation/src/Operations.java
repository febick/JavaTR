
public class Operations {
	/**
	 * 
	 * @param number - given number
	 * @param nBit - given sequential number of bit from 0
	 * @return value of nBit in the given number (see tests)
	 */
	public static int getBit(int number, int nBit) {
		int bitValue = (number << ~nBit < 0) ? 1 : 0;
		return bitValue;
	}
	/**
	 * 
	 * @param number - given number
	 * @param nBit
	 * @return new number (as a particular case it may be the same number)
	 *  with value of the given bit - 1 (see tests)
	 */
	public static int setBit(int number, int nBit) {
		int numberRes = number;

		int newValue = 1 << nBit;
		numberRes = numberRes | newValue;
		
		return numberRes;
	}
	/**
	 * 
	 * @param number - given number
	 * @param nBit
	 * @return new number (as a particular case it may be the same number)
	 *  with value of the given bit - 0 (see tests)
	 */
	public static int resetBit(int number, int nBit) {
		int numberRes = number;

		int newValue = 1 << nBit;
		numberRes = numberRes ^ newValue;

		return numberRes;
	}
	
	/**
	 * 
	 * @param n
	 * @return logN - power of 2 to get n (see tests)
	 */
	public static int log2(long n) {
		// Храним в переменной кол-во итераций
		var result = 0;
		
		// Проверяем не является ли число отрицательным, в этом случае - меняем знак, т.к. нельзя вычитать логорифм у отрицательного числа
		var tmpValue = n < 0 ? -n : n;
		
		// Вычисляем нужное количество итераций для возведения в степень
		do {
			tmpValue = tmpValue / 2;
			result++;	
		} while (tmpValue > 1);
		
		
		return result;
	}



}
