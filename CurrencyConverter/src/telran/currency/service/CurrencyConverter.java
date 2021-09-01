package telran.currency.service;

import java.util.*;

public interface CurrencyConverter {
	Double convert(String from, String to, double amonut);
	Map<String, List<String>> strongestCurrencies(int amount);
	Map<String, List<String>> weakestCurrencies(int amount);
}