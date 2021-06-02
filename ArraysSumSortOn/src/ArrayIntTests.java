import static org.junit.Assert.assertEquals;
import org.junit.Assert;

import org.junit.jupiter.api.Test;

class ArrayIntTests {

	@Test
	void isSum2Test() {
		
		short arrayReturningTrue[] = { 210, 1, 123, 14, 56, 87, 94 };
		short arrayReturningFalse[] = { 1, 2, 3, 4, 5, 9, 8, 7, 6 };
		
		assertEquals(true, Main.isSum2(arrayReturningTrue, (short) 57));
		assertEquals(false, Main.isSum2(arrayReturningFalse, (short) 18));
		
	}
	
	@Test
	void sortArrayTeset() {
		
		short unsortedArray[] = { 9, 0, 4, 1, 3, 2, 5, 7, 8, 6 };
		short sortedArray[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		
		Assert.assertArrayEquals(sortedArray, Main.sort(unsortedArray));
		
	}



}

//1
//14
//56
//87
//94
//123
//210
