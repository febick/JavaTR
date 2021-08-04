package telran.messure;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LenghtTest {

	@Test
	void convertTest() {
		Lenght obj = new Lenght(10, LenghtUnit.M);
		assertEquals(1000, obj.convert(LenghtUnit.CM).getLenght());
	}
	
	@Test
	void compareTest() {
		Lenght obj1 = new Lenght(10, LenghtUnit.M);
		Lenght obj2 = new Lenght(10, LenghtUnit.CM);
		assertEquals(1, obj1.compareTo(obj2));

		Lenght obj3 = new Lenght(10, LenghtUnit.M);
		Lenght obj4 = new Lenght(100, LenghtUnit.M);
		assertEquals(-1, obj3.compareTo(obj4));
		
		Lenght obj5 = new Lenght(100, LenghtUnit.M);
		Lenght obj6 = new Lenght(100, LenghtUnit.M);
		assertEquals(0, obj5.compareTo(obj6));
	}
	
	@Test
	void betweenTest() {
		Lenght obj1 = new Lenght(10, LenghtUnit.M);
		Lenght obj2 = new Lenght(100, LenghtUnit.M);
		var res = LenghtUnit.M.between(obj1, obj2);
		
		assertEquals(90, res);
	}

}
