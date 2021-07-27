package talran.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import telran.utils.MemoryService;

class MemoryServiceTest {

	byte ar[];
	
	@Test
	void getMaxAvailableMemoryTest() {
		var maxMemory = MemoryService.getMaxAvailableMemory();
		boolean arrayCreated = false;
		try {
			@SuppressWarnings("unused")
			byte ar[] = new byte[maxMemory + 1];
			ar = null;
			arrayCreated = true;
			fail("Ошибка: получен массив превышающий максимальный размер");
		} catch (OutOfMemoryError e) {
			assertFalse(arrayCreated);
		}
		
		try {
			@SuppressWarnings("unused")
			byte ar[] = new byte[maxMemory];
			ar = null;
			arrayCreated = true;
			assertTrue(arrayCreated);
		} catch (OutOfMemoryError e) {
			fail("Не смогли построить массив максимально возможного размера");
		}
	}

}
