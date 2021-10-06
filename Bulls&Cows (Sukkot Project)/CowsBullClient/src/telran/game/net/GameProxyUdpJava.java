package telran.game.net;

import java.time.LocalDateTime;

import static telran.game.api.RequestTypesApi.*;
import telran.game.dto.*;
import telran.game.services.GameProcessing;
import telran.net.UdpJavaClient;

public class GameProxyUdpJava extends UdpJavaClient implements GameProcessing {

	private static final long serialVersionUID = 1L;

	public GameProxyUdpJava(String host, int port) throws Exception {
		super(host, port);
	}

	@Override
	public UserCodes registration(String id, String userName) throws Exception {
		return send(REGISTRATION, new UserRequest(id, userName));
	}
	
	@Override
	public UserCodes isUserExists(String userId) throws Exception {
		return send(IS_USER_EXIST, userId);
	}

	@Override
	public String startNewGame(String userId) throws Exception {
		return send(START_NEW_GAME, userId);
	}

	@Override
	public GameStory move(String gameId, String combination) throws Exception {
		return send(MOVE, new MoveRequest(gameId, combination));
	}

	@Override
	public HistoryList getHistory(String userId, LocalDateTime from, LocalDateTime to) throws Exception {
		return send(GET_HISTORY, new HistoryRequest(userId, from, to));
	}
	
	@Override
	public boolean exitFromGame(String gameId) throws Exception {
		return send(EXIT_FROM_GAME, gameId);
	}

	@Override
	public void saveGameResult(GameStory game) {}


}
