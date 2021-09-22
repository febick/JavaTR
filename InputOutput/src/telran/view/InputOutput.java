package telran.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public interface InputOutput {

	String readString(String prompt);
	void writeObject(Object object);

	default void writeObjectLine(Object object) {
		writeObject(object);
		writeObject("\n");
	}

	default <R> R readObject(String prompt, String promptError, Function<String, R> mapper) {
		while (true) {
			try {
				String string = readString(prompt);
				if (string == null) { break; }
				R res = mapper.apply(string);
				return res;
			} catch (Exception e) {
				writeObjectLine(promptError);
			} 
		}
		throw new EndOfInputException();
	}

	default Integer readInt(String promt) {
		return readObject(promt, "No integer number.", Integer::parseInt);
	}

	default Integer readInt(String promt, int min, int max) {
		return readObject(promt, String.format("No number in [%d-%d]\n", min, max + 1), p -> {
			Integer res = Integer.parseInt(p);
			if (res < min || res > max)
				throw new IllegalArgumentException();
			return res;
		});
	}

	default String readStringOption(String promt, Set<String> options) {
		return readStringPredicate(promt, "Entered string is not among the options.", str -> options.contains(str));
	}

	default Long readLong(String promt) {
		return readObject(promt, "No integer number.", Long::parseLong);
	}
	
	default Double readDouble(String promt) {
		return readObject(promt, "No number.", Double::parseDouble);
	}

	default LocalDate readDate(String promt) {
		return readObject(promt, "Wrong date [YYYY-MM-DD] or any other ISO format", LocalDate::parse);
	}
	
	default LocalDateTime readDateTime(String promt) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return readObject(promt, "Date format example: YYYY-MM-DD HH:mm", e -> LocalDateTime.parse(e, formatter));
	}

	default LocalDate readDate(String promt, String formatPattern) {
		return readObject(promt, "Wrong date in format " + formatPattern,
				p -> LocalDate.parse(p, DateTimeFormatter.ofPattern(formatPattern)));
	}
	
	default String readStringPredicate(String prompt, String promptError, Predicate<String> predicate) {
		return readObject(prompt, promptError, str -> {
			if (!predicate.test(str)) { throw new IllegalArgumentException(); }
			return str;
		});
	}


}
