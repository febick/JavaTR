package telran.printer.system;

public class Printer extends Thread {

	public int symbol;
	private int max;
	private int portion;
	private int printedCount;
	public Printer next;

	public Printer(int symbol, int max, int portion) {
		this.symbol = symbol;
		this.max = max;
		this.portion = portion;
	}

	public void setLinkToNext( Printer next) {
		this.next = next;
	}

	@Override
	public void run() {		
		while (true) {
			try {
				Thread.sleep(Integer.MAX_VALUE);
			} catch (InterruptedException e) {
				displayNextPortion();
				next.interrupt();
				if (printedCount >= max) { break; }
			}
		}
	}
	
	private void displayNextPortion() {
		var maxSymbolsToPrint = max - printedCount;
		var count = maxSymbolsToPrint > portion ? portion : maxSymbolsToPrint;
		for (int i = 0; i < count; i++) {
			System.out.printf("%d;", symbol);
			printedCount++;
		}
		System.out.println("");
	}

	
}




