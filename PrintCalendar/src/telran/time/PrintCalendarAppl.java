package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class PrintCalendarAppl {
	
	private static LocalDate currentDay;
	private static int amountOfDays;
	
	public static void printCalendar() {
		currentDay = LocalDate.now();
		currentDay.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
		printCalendar(currentDay.getMonth().getValue(), currentDay.getYear());
	}

	public static void printCalendar(int year) {
		var current = LocalDate.of(year, 1, 1);
		for (int i = 0; i < 12; i++) {
			printCalendar(current.getMonth().getValue(), year);
			current = current.plusMonths(1);
		}
	}
	
	public static void printCalendar(int month, int year) {
		currentDay = LocalDate.of(year, month, 1);
		amountOfDays = currentDay.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
		printTitle();
		printWeekDays();
	}

	private static void printWeekDays() {
		int count = 0;
		do {
			System.out.print(currentDay.getDayOfMonth());
			System.out.printf("%s", currentDay.getDayOfWeek() == DayOfWeek.SUNDAY  ? "\n" : "\t");
			currentDay = currentDay.plusDays(1);
			count++;
		} while (count != amountOfDays);
	}
	
	private static void printTitle() {
		System.out.printf("\n%s (%s):\n\n", currentDay.getMonth(), currentDay.getYear());
		System.out.println("Пн\tВт\tСр\tЧт\tПт\tСб\tВс");
		int separatorCount = currentDay.getDayOfWeek().getValue() - 1;
		for (int i = 0; i < separatorCount; i++) {
			System.out.print("\t");
		}
	}
	

	
}
