
public class Main {

	public static void main(String[] args) {


	}

	public static boolean isSum2(short ar[], short sum) {

		short array[] = sort(ar);
		int left = 0;
		int right = array.length - 1;
		
		while (left < right) {
			
			short result = (short) (array[left] + array[right]);
			
			if (result > sum) {
				right--;
			} else if (result < sum) {
				left++;
			} else {
				return true;
			};
			
		};
		
		return false;

	}

	public static short[] sort(short ar[]) {

		short tmpValue;
		int testingItertion = 0;
		int loopIteration = 0;

		while (testingItertion < ar.length - 1) {

			if (loopIteration == ar.length) {
				testingItertion++;
				loopIteration = testingItertion;
			} 

			// Меняем элементы местами
			if (ar[testingItertion] > ar[loopIteration]) {
				tmpValue = ar[testingItertion];
				ar[testingItertion] = ar[loopIteration];
				ar[loopIteration] = tmpValue;
			} 

			loopIteration++;
		}

		return ar;

	}

}
