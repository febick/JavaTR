package telran.game.dto;

import java.io.Serializable;

public class MoveResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public int bulls;
	public int cows;
	public String combination;
	
	public MoveResult(int bulls, int cows, String combination) {
		this.bulls = bulls;
		this.cows = cows;
		this.combination = combination;
	}
	
	@Override
	public String toString() {
		return String.format("%s - Bulls: %d, Cows: %d", combination, bulls, cows);
	}
	
}
