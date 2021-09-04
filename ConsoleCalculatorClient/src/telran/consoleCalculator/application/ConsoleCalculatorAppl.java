package telran.consoleCalculator.application;
import java.io.IOException;

import telran.calculcator.actions.ServerCalculatorActions;
import telran.consoleCalculator.service.ClientCalculatorTcp;
import telran.view.*;

public class ConsoleCalculatorAppl {

	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 2000;

	public static void main(String[] args) {

		ClientCalculatorTcp connection;
		
		if (args.length < 2) {
			connection = new ClientCalculatorTcp(DEFAULT_HOST, DEFAULT_PORT);
		} else {
			connection = new ClientCalculatorTcp(args[0], Integer.parseInt(args[1]));
		}

		try {
			connection.connect();
			var menuActions = new ServerCalculatorActions(connection);
			var menu = new Menu("Server Calculator", menuActions.getActions());

			while (true) {
				menu.perform(new ConsoleInputOutput());
				var line = connection.read();
				if (line == null ) { 
					System.out.println("Connection are closed.");
					break; 
				}
			}
		} catch (IOException e) {
			System.out.println("Can't connect to the calculator-server.");
		} 

	}

}
