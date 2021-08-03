package telran.text;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BasicCalculatorTests {

	@Test
	void calculatorTest() {
		assertEquals(4, BasicCalculator.calculate("2+2"));
		assertEquals(20, BasicCalculator.calculate("2*10"));
		assertEquals(5, BasicCalculator.calculate("10/2"));
		assertEquals(0, BasicCalculator.calculate("2-2"));
		assertEquals(13, BasicCalculator.calculate("15-2"));
		assertEquals(20, BasicCalculator.calculate("3+17"));
		assertEquals(4, BasicCalculator.calculate(" 2+2"));
		assertEquals(4, BasicCalculator.calculate(" 2 + 2"));

		assertEquals(-19, BasicCalculator.calculate("   3 +4/7 -20"));
		assertEquals(20, BasicCalculator.calculate("   3 +4/7 *20"));
		assertEquals(Integer.MAX_VALUE, BasicCalculator.calculate("3 +4/0 *20"));
		try {
			BasicCalculator.calculate(" * 25");
			fail("Illegal argument exception is required");
		} catch(IllegalArgumentException e) {
			
		}
		
	}

}
