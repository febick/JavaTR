package telran.time;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Month;
import java.time.Year;

import org.junit.jupiter.api.Test;

class PrintCalendarTest {

	@Test
	void printCalendarTest() {
//		Calendar.printCalendarFor(Month.AUGUST, 2021);
		Calendar.printCalendarFor(2022);
	}

}
