package telran.game.exception;

public class UserInUnfinishedGameException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UserInUnfinishedGameException(String string) {
		super(string);
	}

}
