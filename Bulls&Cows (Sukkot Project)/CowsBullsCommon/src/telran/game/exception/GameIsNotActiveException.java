package telran.game.exception;

public class GameIsNotActiveException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public GameIsNotActiveException(String string) {
		super(string);
	}

}
