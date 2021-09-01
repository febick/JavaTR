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
		return rates.entrySet().stream()
				.sorted((e1, e2) -> Double.compare(e1.getValue(), e2.getValue()))
				.filter(p -> currencyCountries.containsKey(p.getKey()))
				.limit(amount)
				.collect(Collectors.toMap(e -> e.getKey(), e -> currencyCountries.get(e.getKey())));		
	}


	@Override
	public Map<String, List<String>> weakestCurrencies(int amount) {
		return rates.entrySet().stream()
				.sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
				.filter(p -> currencyCountries.containsKey(p.getKey()))
				.limit(amount)
				.collect(Collectors.toMap(e -> e.getKey(), e -> currencyCountries.get(e.getKey())));
	}

	abstract protected void refresh();

}
