package telran.currency.service;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractCurrencyConverter extends CurrencyCountryMapper {

	private Map<String, Double> rates;

	protected AbstractCurrencyConverter(Map<String, Double> rates) {
		this.rates = rates;
	}

	@Override
	public Double convert(String from, String to, double amonut) {
		refresh();
		var fromInRate = rates.get(checkCountryOrCurrency(from));
		var toInRate = rates.get(checkCountryOrCurrency(to));
		return (fromInRate / toInRate) * amonut;
	}

	private String checkCountryOrCurrency(String value) {
		if (rates.containsKey(value)) { return value; } 
		if (countryCurrency.containsKey(value)) {
			return countryCurrency.get(value);
		} else {
			throw new RuntimeException("Wrong currency code or county name.");
		}

	}

	@Override
	public Map<String, List<String>> strongestCurrencies(int amount) {
		return createMap(amount, Comparator.naturalOrder());
	}

	@Override
	public Map<String, List<String>> weakestCurrencies(int amount) {
		return createMap(amount, Comparator.reverseOrder());
	}
	
	private Map<String, List<String>> createMap(int amount, Comparator<Double> comparator) {
		return rates.entrySet().stream()
		.sorted(Map.Entry.comparingByValue(comparator))
		.filter(p -> currencyCountries.containsKey(p.getKey()))
		.limit(amount)
		.collect(Collectors.toMap(e -> e.getKey(), e -> currencyCountries.get(e.getKey())));
	}

	abstract protected void refresh();

}
