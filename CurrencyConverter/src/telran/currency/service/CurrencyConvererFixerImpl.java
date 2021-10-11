package telran.currency.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
				String[] splittedResponse = response.body().split("\"rates\":");
				
				lastUpdate = getLastUpdateTime(splittedResponse[0]);
				var rates = parseRates(splittedResponse[1]);
				
				return rates;
			} else {
				throw new RuntimeException("API-key in invalid.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Service is unavailable");
		} 
	}
	
	private static LocalDateTime getLastUpdateTime(String line) {
		var splittedData = line.split(",");
		long serverTime = Long.parseLong(splittedData[1].split(":")[1]) * 1000;
		var lastUpdate = Instant.ofEpochMilli(serverTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
		return lastUpdate;
	}
	
	private static Map<String, Double> parseRates(String data) {
		Map<String, Double> map = new HashMap<>();
		String rates = data.replace("{", "").replace("}", "").replace("\"", "");
		
		Pattern pattern = Pattern.compile(",");
		
		pattern.splitAsStream(rates).map(e -> {
			return e.split(":");
		}).forEach(e -> {
			map.put(e[0], Double.parseDouble(e[1]));
		});
		return map;
	}

	@Override
	protected void refresh() {
		long diff = ChronoUnit.MINUTES.between(lastUpdate, LocalDateTime.now());
		if (diff > 60) { updateRates(getRates()); } 
	}

}
