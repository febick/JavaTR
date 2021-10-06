package telran.game.services;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

import telran.game.dto.*;

public interface GameProcessing extends Serializable {
	
	UserCodes registration(String id, String userName) throws Exception;
	UserCodes isUserExists(String userId) throws Exception;
	String startNewGame(String userId) throws Exception;
	GameStory move(String gameId, String combination) throws Exception;
	HistoryList getHistory(String userId, LocalDateTime from, LocalDateTime to) throws Exception;
	void saveGameResult(GameStory game);
	boolean exitFromGame(String gameId) throws Exception;
	
	default String generateGameId() {
		var uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	default String generateSecret() {
		var builder = new StringBuilder();
		var secret = new Random().ints(1, 10).distinct().limit(4);
		secret.forEach(builder::append);
		return builder.toString();
	}
	
	default GameStory createNewGame(String userId) {
		var id = generateGameId();
		var secret = generateSecret();
		System.out.printf("Running new game for user %s with secret %s\n", userId, secret);
		return new GameStory(secret.toCharArray(), LocalDateTime.now(), id, userId);
	}
	
	default GameStory createMoveResponse(GameStory currentGame) {
		GameStory response = new GameStory(currentGame.getSecret(), currentGame.getDate(), currentGame.getId(), currentGame.getUserId());
		currentGame.getHistory().forEach(response::addMove);
		return response;
	}
	
	default MoveResult checkCombination(GameStory currentGame, String combination) {
		int bulls = 0;
		int cows = 0;

		var checkedCombination = combination.toCharArray();
		var rightCombination = currentGame.getSecret();
		
		var size = checkedCombination.length;
		for (int i = 0; i < size; i++) {
			var checkedChar = checkedCombination[i];
			for (int j = 0; j < 4; j++) {
				var rightChar = rightCombination[j];
				if (rightChar == checkedChar) {
					if (i == j) { bulls += 1; } else { cows += 1; }
					break;
				}
			}
		}
		
		return new MoveResult(bulls, cows, combination);
	}
	
	default GameStory openStoryFile(Path path) throws Exception {
		try (var input = new ObjectInputStream(new FileInputStream(path.toString()))) {
			return (GameStory) input.readObject();
		} 
	}
	
	default <T> void save(T object, String path) {
		try (var output = new ObjectOutputStream(new FileOutputStream(path))) {
			output.writeObject(object);
		} catch (Exception e) {
			System.out.printf("Can't save file to path \"%s\": %s\n", path, e.getLocalizedMessage());
		} 
	}
}
