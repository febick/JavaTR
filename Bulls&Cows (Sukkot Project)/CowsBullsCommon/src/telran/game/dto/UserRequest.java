package telran.game.dto;

import java.io.Serializable;

public class UserRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String id;
	public String name;
	
	public UserRequest(String id, String name) {
		this.id = id;
		this.name = name;
	}

}
