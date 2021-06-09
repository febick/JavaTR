package telran.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListTest {
	
List<Integer> listInt;

	@BeforeEach
	void setUp() throws Exception {
		listInt =  new ArrayList<>(1);
		listInt.add(1);
		listInt.add(2);
		listInt.add(3);
		listInt.add(4);
		listInt.add(5);
	}
	

	@Test
	void addByIndexTest() {
		
		listInt.add(99, 1);
		assertEquals(99, listInt.get(1));
		
	}
	
	@Test
	void removeTest() {
		assertFalse(listInt.remove(100));
		assertTrue(listInt.remove(1));
	}
	
	@Test 
	void getTest() {
		assertEquals(5, listInt.get(4));
		assertEquals(null, listInt.get(100));
	}

}
