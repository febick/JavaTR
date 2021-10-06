package telran.game.controller;

import telran.game.controller.actions.GameActions;
import telran.game.net.GameProxyTcpJava;
import telran.game.net.GameProxyUdpJava;
import telran.game.services.GameProcessing;
import telran.view.*;

public class GameClientAppl {

	private static InputOutput io = new ConsoleInputOutput();
	private static GameProcessing gameProcessing = null;
	
	public static void main(String[] args) throws Exception {

		try {
//			gameProcessing = new GameProxyTcpJava("localhost", 5000);
			gameProcessing = new GameProxyUdpJava("localhost", 5000);
			Menu menu = new Menu("=== Bulls & Cows ===", getItems());
			menu.perform(io);
		} catch (Exception e) {
			io.writeObjectLine("Can't connect to the Game server: " + e.getLocalizedMessage());
		}

	}

	private static Item[] getItems() throws Exception {
		return GameActions.getGameItems(gameProcessing, io);
	}

}
