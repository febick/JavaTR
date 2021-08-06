package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

public class DateOperations {

	public static int workingDays(LocalDate from, LocalDate to, DayOfWeek[] daysOff) {
		var count = 0;
		LocalDate current = from;
		while (!current.equals(to)) {
			if (!isDayOff(current, daysOff)) { count++; }
			current = current.plusDays(1);
		}
		return count;
	}

	private static boolean isDayOff(LocalDate day, DayOfWeek[] dayOfWeeks) {
		for (DayOfWeek dayOfWeek : dayOfWeeks) {
			if (dayOfWeek == day.getDayOfWeek()) { return true; }
		}
		return false;
	}

	public static LocalDate nextFriday13(LocalDate from) {
		return getNextFriday13(getFirst13(from != null ? from : LocalDate.now()));
	}

	private static LocalDate getNextFriday13(LocalDate day) {
		while (day.getDayOfWeek() != DayOfWeek.FRIDAY) {
			day = day.plusMonths(1);
		}
		return day;
	}

	private static LocalDate getFirst13(LocalDate from) {
		do { from = from.plusDays(1);
		} while (from.getDayOfMonth() != 13);
		return from;
	}
	
	public static void printCalendar(Month mounth, Year year) {
		int yearInt = Integer.parseInt(year.toString());
//		var currentMounth = LocalDate.of(yearInt, mounth, 1);
		var countOfDaysInTheMount = 31; // need to get it from currentMounth
		var currentDay = 1;
		
		System.out.printf("%s (%d):\n", mounth, yearInt);
		System.out.println("Пн\tВт\tСр\tЧт\tПт\tСб\tВс");
		
		while (currentDay != countOfDaysInTheMount) {
			var separator = currentDay % 7 == 0 ? "\n" : "\t";
			System.out.print(currentDay++ + separator);
		}
	}


}
