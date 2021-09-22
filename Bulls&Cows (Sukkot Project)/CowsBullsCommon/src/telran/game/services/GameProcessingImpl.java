package telran.game.services;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;

import telran.game.dto.*;
import telran.game.exception.GameIsNotActiveException;
import telran.game.exception.GettingHistoryException;
import telran.game.exception.UserInUnfinishedGameException;
public class GameProcessingImpl implements GameProcessing {

	private static final long serialVersionUID = 1L;
	private static final String DB_FILE_PATH = "users.db"; // Default path to users DB
	private static final String HISTORY_FILE_PATH = "history/"; // Default path to history files

	private HashMap<String, String> usersList;
	private HashMap<String, String> usersInGames = new HashMap<>(); // User ID, Game ID
	private HashMap<String, GameStory> activeGames = new HashMap<>(); // Game ID, GameStory

	public static GameProcessing load() {
		return new GameProcessingImpl();
	}

	@SuppressWarnings("unchecked")
	private GameProcessingImpl() {
		// Try to load users DB or make new set if it's not exists
		try (var input = new ObjectInputStream(new FileInputStream(DB_FILE_PATH))) {
			usersList = (HashMap<String, String>) input.readObject();
			System.out.println("Loading users database from " + DB_FILE_PATH);
		} catch (Exception e) {
			usersList = new HashMap<>();
		} 
	}

	@Override
	public UserCodes registration(String id, String userName) {
		var userStatus = isUserExists(id);
		if (userStatus == UserCodes.USER_IS_NOT_REGISTERED) {
			usersList.put(id, userName);
			save(usersList, DB_FILE_PATH); // Save changes in local users DB
			userStatus = UserCodes.OK;
		}
		return userStatus;
	}

	@Override
	public String startNewGame(String userId) throws Exception {
		if (usersInGames.containsKey(userId)) {
			throw new UserInUnfinishedGameException("The user is currently in another active game: " + usersInGames.get(userId));
		} else {
			var game = createNewGame(userId);
			var gameId = game.getId();
			activeGames.put(gameId, game); // Add game to list of active games
			usersInGames.put(userId, gameId); // Add user to list of active players
			return gameId;
		}
	}

	@Override
	public GameStory move(String gameId, String combination) throws Exception {
		if (activeGames.containsKey(gameId)) {
			
			var currentGame = activeGames.get(gameId);
			MoveResult moveResult = checkCombination(currentGame, combination);
			currentGame.addMove(moveResult);
			
			// Mark game finished if need it
			if (currentGame.lastMove().bulls == 4) {
				finishGame(currentGame, true); 
			}
			
			return createMoveResponse(currentGame);
			
		} else {
			throw new GameIsNotActiveException("This game is inactive.");
		}
	}

	private void finishGame(GameStory currentGame, boolean withSaving) {
		if (withSaving) saveGameResult(currentGame); // Save file with the game history
		usersInGames.remove(currentGame.getUserId()); // Remove user from list of active players
		activeGames.remove(currentGame.getId()); // Remove game from list of active games
	}

	@Override
	public HistoryList getHistory(String userId, LocalDateTime from, LocalDateTime to) throws Exception {
		
		// User Existence Check
		if (isUserExists(userId) == UserCodes.USER_IS_NOT_REGISTERED) {
			throw new GettingHistoryException("User with specified ID isn't registered.");
		}
		
		// Game History Existence Check
		Path path = Paths.get(HISTORY_FILE_PATH + userId);
		if (!Files.exists(path)) {
			throw new GettingHistoryException("User has not completed any games.");
		}

		// Getting games history
		List<Path> allGamesByUser = Files.walk(path)
			.filter(Files::isRegularFile)
			.collect(Collectors.toList());
		
		// Checking date&time range
		LinkedList<GameStory> selectedGames = new LinkedList<>();
		for (Path gamePath : allGamesByUser) {
			GameStory game = openStoryFile(gamePath);
			var gameDate = game.getDate();
			if (gameDate.isAfter(from) && gameDate.isBefore(to)) {
				selectedGames.add(game);
			}
		}
		selectedGames.sort(Comparator.naturalOrder());
		
		if (selectedGames.size() == 0) {
			throw new GettingHistoryException("There are no games for the specified period.");
		}

		return new HistoryList(userId, selectedGames);
	}

	@Override
	public void saveGameResult(GameStory game) {
		var folderPath = HISTORY_FILE_PATH + game.getUserId();
		
		// Configure filename
		var gameDateTime = game.getDate();
		var fileName = String.format("%s_%s_%s_%s_%s_%s_%s_%s",
				game.getId(),
				game.getUserId(),
				getUserName(game.getUserId()),
				gameDateTime.getYear(),
				gameDateTime.getMonthValue(),
				gameDateTime.getDayOfMonth(),
				gameDateTime.getHour(),
				gameDateTime.getMinute());
		
		// Creating the directory if necessary
		File userHistoryDir = new File(folderPath);
		if (!userHistoryDir.exists()) userHistoryDir.mkdirs();
		
		var fullPath = folderPath + "/" + fileName;
		save(game, fullPath);
	}
	
	private String getUserName(String userId) {
		return usersList.get(userId);
	}

	@Override
	public UserCodes isUserExists(String userId) {
		return usersList.containsKey(userId) ? UserCodes.USER_ALREADY_EXISTS : UserCodes.USER_IS_NOT_REGISTERED;
	}

	@Override
	public boolean exitFromGame(String gameId) {
		GameStory removingGame = activeGames.get(gameId);
		finishGame(removingGame, false);
		return removingGame != null ? true : false;
	}

}
