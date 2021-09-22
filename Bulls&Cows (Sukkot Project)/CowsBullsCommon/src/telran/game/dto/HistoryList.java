package telran.game.dto;

import java.io.Serializable;
import java.util.LinkedList;

public class HistoryList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String userId;
	public LinkedList<GameStory> games;
	
	public HistoryList(String userId, LinkedList<GameStory> games) {
		this.userId = userId;
		this.games = games;
	}

}
