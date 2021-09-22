package telran.game.controller;

import telran.game.net.GameProtocol;
import telran.game.services.GameProcessingImpl;
import telran.net.TcpJavaServer;

public class GameServerAppl {

	public static void main(String[] args) {
		TcpJavaServer server = new TcpJavaServer(
				5000, 
				new GameProtocol(GameProcessingImpl.load()));
		server.run();
	}

}
