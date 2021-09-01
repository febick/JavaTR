package telran.currency.service;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public abstract class CurrencyCountryMapper implements CurrencyConverter {

	protected Map<String, String> countryCurrency;
	protected Map<String, List<String>> currencyCountries;
	protected CurrencyCountryMapper() {
		try (BufferedReader reader = new BufferedReader(new FileReader("codes-all_csv.txt"))) {
			fillCountryCurrency(reader);
			fillCurrencyCountries();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void fillCountryCurrency(BufferedReader reader) {
		countryCurrency = reader.lines()
				.map(line -> line.toUpperCase().split("\t"))
				.filter(array -> array.length > 2)
				.collect(Collectors.toMap(a -> a[0], a -> a[2], (k1, k2) -> k1));
		
	}
	
	protected void fillCurrencyCountries() {
		currencyCountries = countryCurrency.entrySet().stream()
		.collect(
				Collectors.groupingBy(Map.Entry::getValue, 
				Collectors.mapping(Map.Entry::getKey,
				Collectors.toList())));
	}
	
}

