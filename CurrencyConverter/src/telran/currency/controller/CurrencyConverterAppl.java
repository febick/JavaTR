package telran.currency.controller;

import telran.currency.controller.actions.ConverterActions;
import telran.currency.service.*;
import telran.view.*;

public class CurrencyConverterAppl {

	private static InputOutput io = new ConsoleInputOutput();
	private static CurrencyConverter converter = null;

	public static void main(String[] args) throws Exception {

		RatesProvider provider = args.length == 0 ? RatesProvider.FIXER : RatesProvider.LOCAL_FILE;

		switch (provider) {
		case FIXER:
			try {
				converter = CurrencyConvererFixerImpl.getCurrencyConverter();
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				break;
			}
		case LOCAL_FILE:
			try {
				converter = CurrencyConverterFileImpl.getCurrencyConverter(args[0]);
			} catch (Exception e) {
				io.writeObjectLine("File with actual rates not found.\n");
				return;
			}
		}

		Menu menu = new Menu("Currency Converter", getItems());
		menu.perform(io);
	}

	private static Item[] getItems() throws Exception {
		return ConverterActions.getConverterItems(converter);
	}

}
