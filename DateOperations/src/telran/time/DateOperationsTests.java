package telran.time;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

class DateOperationsTests {


	@Test
	void workingDaysTestAdjuster() {
		int[] noDaysOff = {};
		int[] israelDaysOff = { DayOfWeek.FRIDAY.getValue(), DayOfWeek.SATURDAY.getValue() };
		LocalDate from = LocalDate.of(2021, 8, 5);
		LocalDate to = LocalDate.of(2021, 8, 8);
		assertEquals(to, from.with(new WorkingDays(3, noDaysOff)));
		assertEquals(to, from.with(new WorkingDays(1, israelDaysOff)));
	}

	@Test
	void NextFriday13v2() {
		LocalDate from = LocalDate.of(2021, 8, 5);
		LocalDate expected = LocalDate.of(2021, 8, 13);
		assertEquals(expected, from.with(new NextFriday13()));

		LocalDateTime fromLdt = LocalDateTime.of(from, LocalTime.of(0, 0));
		LocalDateTime expectedLdt = LocalDateTime.of(expected, LocalTime.of(0, 0));
		assertEquals(expectedLdt, fromLdt.with(new NextFriday13()));


	}

	@Test 
	void workingDaysTest() {
		DayOfWeek[] weekends = new DayOfWeek[] {DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
		var from = LocalDate.of(2021, Month.JANUARY, 01);
		var to = LocalDate.of(2021, Month.DECEMBER, 31);
		assertEquals(260, DateOperations.workingDays(from, to, weekends));
	}

	@Test
	void nextFriday13Test() {

		var from = LocalDate.of(2021, Month.AUGUST, 13);
		var expected = LocalDate.of(2022, Month.MAY, 13);
		var res = DateOperations.nextFriday13(from);    
		assertEquals(expected, res);

		from = LocalDate.of(2021, Month.SEPTEMBER, 5);
		expected = LocalDate.of(2022, Month.MAY, 13);
		res = DateOperations.nextFriday13(from);
		assertEquals(expected, res);
	}

	//	@Test
	void printCalendar() {
		DateOperations.printCalendar(Month.APRIL, Year.now());
	}

}
