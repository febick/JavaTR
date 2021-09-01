package telran.currency.controller;

import telran.currency.controller.actions.ConverterActions;
import telran.currency.service.*;
import telran.view.*;

public class CurrencyConverterFileAppl {
	
	private static final String DEFAULT_FILE_PATH = "rates_2021-08-28";
	private static InputOutput io = new ConsoleInputOutput();
	private static CurrencyConverter converter = null;

	public static void main(String[] args) {
		String filePath = args.length > 0 ? args[0] : DEFAULT_FILE_PATH;
		
		try {
			converter = CurrencyConverterFileImpl.getCurrencyConverter(filePath);
		} catch (Exception e) {
			io.writeObjectLine("File with actual rates not found.\n");
			return;
		}
		
		Menu menu = new Menu("Currency Converter", getItems());
		menu.perform(io);
	}

	private static Item[] getItems() {
		return ConverterActions.getConverterItems(converter);
	}

}
