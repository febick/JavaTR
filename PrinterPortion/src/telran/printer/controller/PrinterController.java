package telran.printer.controller;

import java.util.*;

import telran.printer.system.Printer;

public class PrinterController {
	
	private static final int N_PRINTERS = 5; 
	private static final int N_NUMBERS = 100; 
	private static final int N_PORTIONS = 5; 
	private static List<Printer> allPrinters;

	public static void main(String[] args) {
		createPrinters();
		setupLinks();
		run();
	}
	
	private static void createPrinters() {
		allPrinters = new ArrayList<Printer>();
		for (int i = 0; i < N_PRINTERS; i++) {
			allPrinters.add(new Printer(i + 1, N_NUMBERS, N_PORTIONS));
		}
	}
	
	private static void setupLinks() {
		var lastPrinterID = N_PRINTERS - 1;
		for (int i = 0; i < N_PRINTERS; i++) {
			var current = allPrinters.get(i);
			var next = allPrinters.get(i == lastPrinterID ? 0 : i + 1);
			current.setLinkToNext(next);
		}
	}
	
	private static void run() {
		 allPrinters.forEach(Printer::start);
		 allPrinters.get(0).interrupt();
	}

}
