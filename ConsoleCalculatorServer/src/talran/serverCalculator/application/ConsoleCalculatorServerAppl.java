package talran.serverCalculator.application;
import java.io.*;
import java.net.*;

import telran.serverCalculator.service.BasicCalculator;

public class ConsoleCalculatorServerAppl {
	
	private static final int PORT = 2000;

	public static void main(String[] args) {
		
		try {
			getSocket();
		} catch (IOException e) {
			System.out.printf("Can't run server on port %d\n: %s", PORT, e.getLocalizedMessage());
		}
		
	}
	
	private static void getSocket() throws IOException {
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.printf("Server is listening on port %d\n", PORT);
		while (true) {
			Socket socket = serverSocket.accept();
			getConnectionWithClient(socket);
		}
	}

	private static void getConnectionWithClient(Socket socket) {
		try (	BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintStream writer = new PrintStream(socket.getOutputStream())) {
			
			while (true) {
				String line = reader.readLine();
				if (line == null || line.equals("exit")) { 
					System.out.println("Client are closed connection.");
					break; 
				} 
				
				tryToCalculate(writer, line);
			}
			
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	private static void tryToCalculate(PrintStream writer, String line) {
		try {
			String cleanExpression = validateArguments(line);
			int response = BasicCalculator.calculate(cleanExpression);
			writer.println(String.format("\tResult: %d", response));
		} catch (IllegalArgumentException e) {
			writer.println("Entered line is not an arithemetic expression.");
		} catch (ArithmeticException e) {
			writer.println(e.getMessage());
		}
	}

	private static String validateArguments(String expression) {
		if (!expression.matches(BasicCalculator.arithemeticExpression())) {
			throw new IllegalArgumentException();
		}
		return expression.replaceAll("\\s", "");
	}
	

}
