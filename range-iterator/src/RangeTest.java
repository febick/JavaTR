import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RangeTest {

	@Test
	void testWhitoutPredicate() throws InvalidArgumentException {
		Range range = new Range(1, 10);
		Integer rightArray[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};		
		var it = range.iterator();
		var currentIndex = 0;
		while (it.hasNext()) {
			assertEquals(rightArray[currentIndex], it.next());
			currentIndex++;
		}
	}
	
	@Test void testWithEvenNumbers() throws InvalidArgumentException {
		Range range = new Range(1, 10);
		range.setPredicate(n -> n % 2 == 0);
		Integer rightArray[] = {2, 4, 6, 8, 10};		
		var it = range.iterator();
		var currentIndex = 0;
		while (it.hasNext()) {
			assertEquals(rightArray[currentIndex], it.next());
			currentIndex++;
		}
	}
	
	@Test void testWithOddNumbers() throws InvalidArgumentException {
		Range range = new Range(1, 10);
		range.setPredicate(n -> n % 2 != 0);
		Integer rightArray[] = {1, 3, 5, 7, 9};		
		var it = range.iterator();
		var currentIndex = 0;
		while (it.hasNext()) {
			assertEquals(rightArray[currentIndex], it.next());
			currentIndex++;
		}
	}
	
	@Test void testWithSwappedNumbers() throws InvalidArgumentException {
		boolean gotException = false;
		try {
			@SuppressWarnings("unused")
			Range range = new Range(10, 1);
		} catch (InvalidArgumentException e) {
			gotException = true;
		}
		assertTrue(gotException);
	}
	
	@Test void testWithPrimeNumbers() throws InvalidArgumentException {
		Range range = new Range(1, 10);
		range.setPredicate(new PrimeNumbersPredicate());
		Integer rightArray[] = {2, 3, 5, 7};		
		var it = range.iterator();
		var currentIndex = 0;
		while (it.hasNext()) {
			assertEquals(rightArray[currentIndex], it.next());
			currentIndex++;
		}
	}
	
	@Test void testWithNotPrimeNumbers() throws InvalidArgumentException {
		Range range = new Range(1, 10);
		range.setPredicate(new PrimeNumbersPredicate().negate());		
		Integer rightArray[] = {1, 4, 6, 8, 9, 10};		
		var it = range.iterator();
		var currentIndex = 0;
		while (it.hasNext()) {
			assertEquals(rightArray[currentIndex], it.next());
			currentIndex++;
		}
	}
	
}
