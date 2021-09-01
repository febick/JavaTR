package telran.currency.service;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

public class CurrencyConverterFileImpl extends AbstractCurrencyConverter {

	private CurrencyConverterFileImpl(Map<String, Double> rates) {
		super(rates);
	}

	public static CurrencyConverter getCurrencyConverter(String filePath) {
		return new CurrencyConverterFileImpl(getRates(filePath));
	}

	@SuppressWarnings({ "unchecked", "resource" })
	private static Map<String, Double> getRates(String filePath) {
		try {
			ObjectInputStream reader = new ObjectInputStream(new FileInputStream(filePath));
			return (Map<String, Double>) reader.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void refresh() {}

}
