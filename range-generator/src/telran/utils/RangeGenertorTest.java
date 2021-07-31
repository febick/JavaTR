package telran.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RangeGenertorTest {

	@Test
	void checkTest() {
		Rule rule = new DividerRule(2);

		// Check correct value
		try {
			rule.check(16, 2, 20);
		} catch (NoRuleMatchException e) {
			fail();
		} 

		// Check illegal range arguments
		try {
			rule.check(16, 20, 18);
			fail();
		} catch (NoRuleMatchException e) {
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Min can't be greater or equal than max.", e.getMessage());
		}
	}

	@Test
	void findNearestDeltaTest() {
		Rule rule = new DividerRule(3);

		var correctRigthDelta = 1;
		var correctLeftDelta = -3;

		// Check rigth delta
		try {
			rule.check(17, 2, 20);
			fail();
		} catch (NoRuleMatchException e) {
			assertEquals(correctRigthDelta, e.getDelta());
		}

		// Check left delta
		try {
			rule.check(21, 2, 20);
			fail();
		} catch (NoRuleMatchException e) {
			assertEquals(correctLeftDelta, e.getDelta());
		}
	}
	
	@Test
	void generatorTest() {
		Rule rule = new DividerRule(3);
		Generator generator = new Generator();
		generator.setRule(rule);
		
		var range = generator.generate(5, 10, 40);

		for (int value : range) {
			try {
				rule.check(value, 10, 40);
			} catch (NoRuleMatchException e) {
				System.out.println("Fail in value " + value);
				fail();
			}
		}
		
	}

}
