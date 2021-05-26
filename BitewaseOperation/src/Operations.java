
public class Operations {
	/**
	 * 
	 * @param number - given number
	 * @param nBit - given sequential number of bit from 0
	 * @return value of nBit in the given number (see tests)
	 */
	public static int getBit(int number, int nBit) {
		int bitValue = 0;
		
		//here your code goes 
		if (number << ~nBit < 0) {
			bitValue = 1;
		}
		
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
		
		//here your code goes 
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
		
		//here your code goes 
		int newValue = 1 << nBit;
		numberRes = numberRes ^ newValue;

		return numberRes;
	}


}
