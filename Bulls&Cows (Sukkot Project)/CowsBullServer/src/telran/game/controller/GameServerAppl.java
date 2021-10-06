package telran.game.controller;

import telran.game.net.GameProtocol;
import telran.game.services.GameProcessingImpl;
import telran.net.TcpJavaServer;
import telran.net.UdpServerJava;

public class GameServerAppl {

	public static void main(String[] args) throws Exception {
//		TcpJavaServer server = new TcpJavaServer(5000, new GameProtocol(GameProcessingImpl.load()));
		UdpServerJava server = new UdpServerJava(5000, new GameProtocol(GameProcessingImpl.load()));
		server.run();
	}

}
