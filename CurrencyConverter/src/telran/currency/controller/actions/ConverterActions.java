package telran.currency.controller.actions;

import java.util.*;
import telran.currency.service.CurrencyConverter;
import telran.view.*;

public class ConverterActions {
	
	private static CurrencyConverter converter;
	
	private ConverterActions() {}
	
	public static Item[] getConverterItems(CurrencyConverter converter) {
		ConverterActions.converter = converter;
		return getItems();
	}

	private static Item[] getItems() {
		List<Item> res = new ArrayList<>();
		res.add(Item.of("Convert currency", ConverterActions::convertAction));
		res.add(Item.of("Show strongest currencies", ConverterActions::getStrongest));
		res.add(Item.of("Show weakest currencies", ConverterActions::getWeakest));
		res.add(Item.exit());
		return res.toArray(new Item[0]);
	}
	
	private static void convertAction(InputOutput io) {
		var from = io.readString("Enter first country name or currency code: ");
		var to = io.readString("Enter second country name or currency code: ");
		var amount = io.readDouble("Enter amount of money: ");
		var res = converter.convert(from.toUpperCase(), to.toUpperCase(), amount);
		io.writeObjectLine("Result: " + res);
	}
	
	private static void getStrongest(InputOutput io) {
		var amount = io.readInt("Enter count of currencies: ");
		var res = converter.strongestCurrencies(amount);
		io.writeObjectLine("Strongest currencies:\n");
		desplayList(io, res);
	}
	
	private static void getWeakest(InputOutput io) {
		var amount = io.readInt("Enter count of currencies: ");
		var res = converter.weakestCurrencies(amount);
		io.writeObjectLine("Weakest currencies:\n");
		desplayList(io, res);}

	private static void desplayList(InputOutput io, Map<String, List<String>> map) {
		map.forEach((country, currency) -> {
			io.writeObjectLine(String.format("%s - %s", country, currency));
		});
	}
	
	

}
