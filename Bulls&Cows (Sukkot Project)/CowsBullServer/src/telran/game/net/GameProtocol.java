package telran.game.net;

import telran.game.dto.*;
import telran.game.services.GameProcessing;
import telran.net.ApplProtocolJava;
import static telran.game.api.RequestTypesApi.*;

import java.io.Serializable;

import telran.net.dto.*;

public class GameProtocol implements ApplProtocolJava {

	GameProcessing gameProcessing;
	
	public GameProtocol(GameProcessing gameProcessing) {
		this.gameProcessing = gameProcessing;
	}
	
	@Override
	public ResponseJava getResponse(RequestJava request) {
		switch (request.requestType) {
		case REGISTRATION: return user_add(request.data);
		case IS_USER_EXIST: return user_exist(request.data);
		case START_NEW_GAME: return game_new(request.data);
		case MOVE: return game_move(request.data);
		case EXIT_FROM_GAME: return game_exit(request.data);
		case GET_HISTORY: return game_history(request.data);
		default: return new ResponseJava(ResponseCode.WRONG_REQUEST_TYPE, request.requestType);
		}
	}
	
	private ResponseJava user_add(Serializable data) {
		try {
			var user = (UserRequest) data;
			return new ResponseJava(ResponseCode.OK,
					gameProcessing.registration(user.id, user.name));
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}
	
	private ResponseJava user_exist(Serializable data) {
		try {
			return new ResponseJava(ResponseCode.OK,
					gameProcessing.isUserExists( (String) data));
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}

	private ResponseJava game_new(Serializable data) {
		try {
			return new ResponseJava(ResponseCode.OK, 
				gameProcessing.startNewGame( (String) data));
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}

	private ResponseJava game_move(Serializable data) {
		var moveRequest = (MoveRequest) data;
		try {
			return new ResponseJava(ResponseCode.OK, 
					gameProcessing.move(moveRequest.gameId, moveRequest.combination));
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}

	private ResponseJava game_history(Serializable data) {
		var historyRequest = (HistoryRequest) data;
		try {
			return new ResponseJava(ResponseCode.OK, 
				gameProcessing.getHistory(historyRequest.userId, historyRequest.from, historyRequest.to));
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}
	
	private ResponseJava game_exit(Serializable data) {
		try {
			return new ResponseJava(ResponseCode.OK, 
				gameProcessing.exitFromGame( (String) data ));
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}
	
	private ResponseJava getWrongDataResponse(Exception e) {
		return new ResponseJava(ResponseCode.WRONG_REQUEST_DATA, e);
	}

}
