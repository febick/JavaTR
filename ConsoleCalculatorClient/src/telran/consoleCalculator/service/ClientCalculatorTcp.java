package telran.consoleCalculator.service;

import java.net.*;
import java.io.*;

public class ClientCalculatorTcp {

	private static String host;
	private static int port;
	private static Socket socket;
	private static PrintStream socketWriter;
	private static BufferedReader socketReader;

	public ClientCalculatorTcp(String host, int port) {
		ClientCalculatorTcp.host = host;
		ClientCalculatorTcp.port = port;
	}

	public void connect() throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		socketWriter = new PrintStream(socket.getOutputStream());
		socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void write(String line) {
		socketWriter.println(line);
	}
	
	public String read() throws IOException {
		return socketReader.readLine();
	}

}


