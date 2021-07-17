import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LineryRecursionTest {

	@Test
	void powTest() {
		assertEquals(9, LineryRecursion.pow(3, 2));
		assertEquals(256, LineryRecursion.pow(4, 4));
		assertEquals(8, LineryRecursion.pow(2, 3));
		assertEquals(100, LineryRecursion.pow(10, 2));
		assertEquals(1000, LineryRecursion.pow(10, 3));
		assertEquals(4, LineryRecursion.pow(2, 2));
		assertEquals(216, LineryRecursion.pow(6, 3));
		assertEquals(7776, LineryRecursion.pow(6, 5));
		assertEquals(4096, LineryRecursion.pow(4, 6));
		assertEquals(512, LineryRecursion.pow(8, 3));
	}
	
	@Test
	void sumTest() {
		assertEquals(10, LineryRecursion.sum(new int[] {1, 2, 3, 4}));
	}
	
	@Test
	void squareTest() {
		assertEquals(25, LineryRecursion.square(5));
		assertEquals(25, LineryRecursion.square(-5));
		assertEquals(36, LineryRecursion.square(6));
		assertEquals(49, LineryRecursion.square(7));
		assertEquals(64, LineryRecursion.square(8));
		assertEquals(81, LineryRecursion.square(9));
		assertEquals(100, LineryRecursion.square(10));
	}

	@Test
	void isSubstring() {
		assertTrue(LineryRecursion.isSubstring("Hello", "llo"));
		assertTrue(LineryRecursion.isSubstring("Hello", "lo"));
//		assertTrue(LineryRecursion.isSubstring("Hello", "el"));
		assertTrue(LineryRecursion.isSubstring("Hello", "Hello"));
		assertFalse(LineryRecursion.isSubstring("Hello", "Helo"));
		assertFalse(LineryRecursion.isSubstring("aba", "aa"));	
		assertFalse(LineryRecursion.isSubstring("aba", "aaaa")); 
	}

}
