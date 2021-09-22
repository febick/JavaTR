package telran.game.controller.actions;

import java.time.LocalDateTime;
import java.util.*;

import telran.game.dto.*;
import telran.game.services.GameProcessing;
import telran.view.*;

public class GameActions {

	private static GameProcessing gameProcessing;
	private static InputOutput io;

	private GameActions() {};

	public static Item[] getGameItems(GameProcessing gameProcessing, InputOutput io) throws Exception {
		GameActions.gameProcessing = gameProcessing;
		GameActions.io = io;
		return selectItems();
	}

	private static Item[] selectItems() throws Exception {
		List<Item> res = new ArrayList<>();

		res.add(Item.of("Registration", t -> {
			try {
				registration(t);
			} catch (Exception e) {
				t.writeObjectLine(e.getMessage());
			}
		}));

		res.add(Item.of("Start New Game", t -> {
			try {
				start(t);
			} catch (Exception e) {
				t.writeObjectLine(e.getMessage());
			}
		}));

		res.add(Item.of("Get Games History", t -> {
			try {
				getHistory(t);
			} catch (Exception e) {
				t.writeObjectLine(e.getMessage());
			}
		}));

		res.add(Item.exit());

		return res.toArray(new Item[0]);
	}

	private static void registration(InputOutput io) throws Exception {
		String userId = io.readString("Create your own unique ID: ");
		String userName = io.readString("Enter your name: ");
		var res = gameProcessing.registration(userId, userName);
		io.writeObjectLine(res == UserCodes.USER_ALREADY_EXISTS ? "User with the same ID already exists." : "You are successfully registered and can start the game!");
	}


	private static void start(InputOutput io) throws Exception {
		String userId = getUserID();
		if (userId == null) { return; }

		String gameId = gameProcessing.startNewGame(userId);
		displayGameStartedMessage();
		
		while (true) {
			var combination = io.readString("\nEnter 4 numbers to check your guess: ");

			// End the game if user entered "0"
			if (combination.equals("0")) { 
				displayGameOverMessage();
				gameProcessing.exitFromGame(gameId);
				break; 
			} 

			// Make the move and getting result 
			var gameStory = gameProcessing.move(gameId, combination); 

			// Display game statistic
			io.writeObject(gameStory);

			// Check to win
			if (gameStory.lastMove().bulls == 4) {
				io.writeObjectLine(String.format("===== You WON in %d moves! =====", gameStory.getHistory().size()));
				displayGameOverMessage();
				break;
			}

			displayExitMessage();
		}
	}

	private static String getUserID() throws Exception {
		String userId = io.readString("Enter your ID: ");
		if (checkUserExisting(userId)) {
			return userId;
		} else {
			io.writeObjectLine("User with specified ID isn't registered.");
			return null;
		}
	}

	private static boolean checkUserExisting(String userId) throws Exception {
		return gameProcessing.isUserExists(userId) == UserCodes.USER_ALREADY_EXISTS ? true : false;
	}

	private static void getHistory(InputOutput io) throws Exception {
		// Getting parameters for request
		String userId = getUserID();
		if (userId == null) { return; }
		LocalDateTime from = io.readDateTime("Enter Date&Time from: ");
		LocalDateTime to = io.readDateTime("Enter Date&Time to: ");
		
		// Get History Data
		HistoryList res = gameProcessing.getHistory(userId, from, to); 
		
		// Print History Data
		res.games.forEach(game -> {
			printSeparator();
			io.writeObjectLine(String.format("Date: %s", game.getDate()));
			io.writeObject(game);
			io.writeObject("\n");
		});
	}

	private static void displayGameStartedMessage() {
		printSeparator();
		io.writeObjectLine("===== The game has started =====");
		printSeparator();
	}

	private static void displayExitMessage() {
		io.writeObjectLine("====== Type \"0\" for exit =======");
		printSeparator();
	}

	private static void displayGameOverMessage() {
		printSeparator();
		io.writeObjectLine("========== GAME  OVER ==========");
		printSeparator();
	}
	
	private static void printSeparator() {
		io.writeObjectLine("================================");
	}
	

}
