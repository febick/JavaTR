package telran.currency.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CurrencyConvererFixerImpl extends AbstractCurrencyConverter {

	private static LocalDateTime lastUpdate;
	private static final String API_URL = "http://data.fixer.io/api/latest?access_key=";
	private static final String API_KEY = "a0e10b012bd76a4854aedbebb204fd27";
	
	private CurrencyConvererFixerImpl(Map<String, Double> rates) {
		super(rates);
	}
	
	public static CurrencyConverter getCurrencyConverter() {
		return new CurrencyConvererFixerImpl(getRates());
	}
	
	private static Map<String, Double> getRates() {
		try {
			HttpResponse<String> response;
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder(new URI(API_URL + API_KEY)).build();
			response = client.send(request, BodyHandlers.ofString());
			if (response.statusCode() == 200) {
				lastUpdate = LocalDateTime.now();
				Map<String, Double> map = new HashMap<>();
				
				String[] splittedResponse = response.body().split("\"rates\":");
				String rates = splittedResponse[1].replace("{", "").replace("}", "").replace("\"", "");
				Pattern pattern = Pattern.compile(",");
				
				pattern.splitAsStream(rates).map(e -> {
					return e.split(":");
				}).forEach(e -> {
					map.put(e[0], Double.parseDouble(e[1]));
				});
				
				return map;
			} else {
				throw new RuntimeException("Service is unavailable");
			}
		} catch (Exception e) {
			throw new RuntimeException("Service is unavailable");
		} 
	}

	@Override
	protected void refresh() {
		long diff = ChronoUnit.MINUTES.between(lastUpdate, LocalDateTime.now());
		if (diff > 60) { updateRates(getRates()); } 
	}

}
