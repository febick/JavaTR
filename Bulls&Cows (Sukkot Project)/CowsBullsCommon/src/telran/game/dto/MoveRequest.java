package telran.game.dto;

import java.io.Serializable;


public class MoveRequest implements Serializable  {

	private static final long serialVersionUID = 1L;
	public String gameId;
	public String combination;
	
	public MoveRequest(String gameId, String combination) {
		this.gameId = gameId;
		this.combination = combination;
	}

}
