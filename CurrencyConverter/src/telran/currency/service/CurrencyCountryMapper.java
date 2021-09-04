package telran.currency.service;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public abstract class CurrencyCountryMapper implements CurrencyConverter {

	protected Map<String, String> countryCurrency;
	protected Map<String, List<String>> currencyCountries;
	protected CurrencyCountryMapper() {
//		try (BufferedReader reader = new BufferedReader(new FileReader("resources/codes-all_csv.txt"))) {
		try (BufferedReader reader = new BufferedReader(getResourceReader())) {
			fillCountryCurrency(reader);
			fillCurrencyCountries();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Reader getResourceReader() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("resources/codes-all_csv.txt");
		return new InputStreamReader(inputStream);
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
	
	@Override
	public String getCurrencyCode(String value) {
		value = value.toUpperCase();
		var res = currencyCountries.containsKey(value) ? value : countryCurrency.get(value);
		if (res == null) { throw new RuntimeException(String.format("%s not found on the currencies and country lists.", value)); }
		return res;
	}
	
	@Override
	public List<String> getCountriesCurrency(String currency) {
		currency = currency.toUpperCase();
		List<String> res = currencyCountries.get(currency);
		if (res == null) {
			throw new RuntimeException(String.format("%s is incorrect currency code.", currency));
		}
		return res;
	}
	
}

