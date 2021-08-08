package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;


public class PrintCalendarAppl {

	private static LocalDate currentDay;
	private static int amountOfDays;
	private static Locale locale = Locale.getDefault();
	private static DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;
	private static DayOfWeek lastDayOfWeek = DayOfWeek.SUNDAY;

	public static void main(String[] args) {
		
		try {
			if (args.length == 0) {
				printCalendar();
			} else if (args.length == 1) {
				printCalendar(Integer.parseInt(args[0]));
			} else if (args.length == 2) {
				printCalendar(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			} else if (args.length == 3) {
				firstDayOfWeek = DayOfWeek.of(castToDayOfWeek(args[2]));
				lastDayOfWeek = findNewLastDay(firstDayOfWeek.getValue());
				printCalendar(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
	}
	
	private static int castToDayOfWeek(String value) {
		String[] names = {"", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
		var chekedString = value.toUpperCase();
		for (int i = 0; i < names.length; i++) {
			if (chekedString.equals(names[i])) {
				return i;
			}
		}
		return 1;
	}

	private static DayOfWeek findNewLastDay(int newFirstDay) {
		int newLastDay = newFirstDay == 1 ? 7 : newFirstDay - 1;
		return DayOfWeek.of(newLastDay);
	}

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
		if (month < 1 || month > 12) {
			throw new IllegalArgumentException("Invalid month argument");
		}
		if (year < 0) {
			throw new IllegalArgumentException("Invalid year argument");
		}
		currentDay = LocalDate.of(year, month, 1);
		amountOfDays = currentDay.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
		printTitle();
		printWeekDays();
	}

	private static void printWeekDays() {
		int count = 0;
		do {
			System.out.print(currentDay.getDayOfMonth());
			System.out.printf("%s", currentDay.getDayOfWeek() == lastDayOfWeek ? "\n" : "\t");
			currentDay = currentDay.plusDays(1);
			count++;
		} while (count != amountOfDays);
		System.out.println("\n");
	}

	private static void printTitle() {
		System.out.printf("\n\t\t%s, %s:\n\n", currentDay.getYear(), currentDay.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, locale));

		DayOfWeek[] weekTitle = DayOfWeek.values();
		DayOfWeek[] fixedTitles = new DayOfWeek[weekTitle.length];

		System.arraycopy(weekTitle, firstDayOfWeek.getValue() - 1, fixedTitles, 0, 8 - firstDayOfWeek.getValue());
		var nullIndex = 0;
		for (DayOfWeek dayOfWeek : fixedTitles) {
			if (dayOfWeek != null) {
				nullIndex++;
			} else { break; }
		}
		System.arraycopy(weekTitle, 0, fixedTitles, nullIndex, 7 - nullIndex);
		
		String fullWeekTitles = "";
		for (DayOfWeek dayOfWeek : fixedTitles) {
			fullWeekTitles += dayOfWeek.getDisplayName(TextStyle.SHORT, locale) + "\t";
		}
		
		System.out.println(fullWeekTitles);
		
		var offsetCount = currentDay.getDayOfWeek().getValue() == 7 ? 0 : 7 - calculateOffset();
		System.out.print("\t".repeat(offsetCount));
		
	}
	
	private static int calculateOffset() {
		LocalDate current = currentDay;
		var index = 1;
		do {
			index++;
			current = current.plusDays(1);
		} while (current.getDayOfWeek() != lastDayOfWeek);
		return index;
	}



}
