package telran.game.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HistoryRequest implements Serializable  {

	private static final long serialVersionUID = 1L;
	public String userId;
	public LocalDateTime from;
	public LocalDateTime to;
	
	public HistoryRequest(String userId, LocalDateTime from, LocalDateTime to) {
		this.userId = userId;
		this.from = from;
		this.to = to;
	}

}
