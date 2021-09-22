package telran.game.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class GameStory implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private LinkedList<MoveResult> moveHistory;
	private LocalDateTime date;
	private String id;
	private char[] secret;
	private String userId;
	
	public GameStory(char[] secret, LocalDateTime date, String id, String userId) {
		this.moveHistory = new LinkedList<MoveResult>();
		this.secret = secret;
		this.date = date;
		this.id = id;
		this.userId = userId;
	}
	
	public LocalDateTime getDate() {
		return date;
	}

	public String getId() {
		return id;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public char[] getSecret() {
		return secret;
	}
	
	public LinkedList<MoveResult> getHistory() {
		return moveHistory;
	}
	
	public MoveResult lastMove() {
		return moveHistory.getLast();
	}
	
	public void addMove(MoveResult move) {
		moveHistory.add(move);
	}
	
	@Override
	public String toString() {
		var builder = new StringBuilder();
		var spacer = "================================\n";
		var title =	 "========== GAME  STAT ==========\n";
		
		builder.append(spacer);
		builder.append(title);
		builder.append(spacer);
		
		int count = 1;
		for (MoveResult moveResult : moveHistory) {
			builder.append(String.format("  %d - %s\n", count++, moveResult.toString()));
		}
		
		builder.append(spacer);
		
		return builder.toString();
	}
	

}
