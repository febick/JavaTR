package telran.currency.service;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public abstract class AbstractCurrencyConverter extends CurrencyCountryMapper {

	private Map<String, Double> rates;

	protected AbstractCurrencyConverter(Map<String, Double> rates) {
		this.rates = rates;
	}

	@Override
	public Double convert(String from, String to, double amonut) {
		refresh();
		var fromInRate = rates.get(checkCountryOrCurrency(from.toUpperCase()));
		var toInRate = rates.get(checkCountryOrCurrency(to.toUpperCase()));
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
		.filter(p -> currencyCountries.containsKey(p.getKey()))
		.sorted(Map.Entry.comparingByValue(comparator))
		.limit(amount)
		.collect(Collectors.toMap(
				Entry::getKey, 
				e -> currencyCountries.get(e.getKey()), 
				(a, b) -> a, LinkedHashMap::new));
	}

	abstract protected void refresh();

}
