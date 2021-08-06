package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

public class Calendar {

	public static void printCalendarFor(int year) {
		var current = LocalDate.of(year, Month.JANUARY, 1);
		for (int i = 0; i < 12; i++) {
			printCalendarFor(current.getMonth(), year);
			current = current.plusMonths(1);
		}
	}
	
	public static void printCalendarFor(Month month, int year) {
		var currentDay = LocalDate.of(year, month, 1);
		int amountOfDays = currentDay.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
		int count = 0;
		
		printHeader(currentDay);
		
		// Print dates
		do {
			System.out.print(currentDay.getDayOfMonth());
			System.out.printf("%s", currentDay.getDayOfWeek() == DayOfWeek.SUNDAY  ? "\n" : "\t");
			currentDay = currentDay.plusDays(1);
			count++;
		} while (count != amountOfDays);
		
		// Print footer
		System.out.println("\n__________________________________________________");

	}
	
	private static void printHeader(LocalDate day) {
		System.out.printf("\n%s (%s):\n\n", day.getMonth(), day.getYear());
		System.out.println("Пн\tВт\tСр\tЧт\tПт\tСб\tВс");
		int separatorCount = day.getDayOfWeek().getValue() - 1;
		for (int i = 0; i < separatorCount; i++) {
			System.out.print("\t");
		}
	}
	

	
}
