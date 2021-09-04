import java.io.*;
import java.net.*;

public class ServerEchoReversAppl {

	private static final int PORT = 2000;

	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.printf("Server is listening on port %d\n", PORT);
		while (true) {
			Socket socket = serverSocket.accept();
			runClient(socket);
		}
	}

	private static void runClient(Socket socket) {
		try (	BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintStream writer = new PrintStream(socket.getOutputStream())) {
			
			while (true) {
				String line = reader.readLine();
				if (line == null) { 
					System.out.println("Client are closed connection");
					break; 
				} 
				String response = new StringBuilder(line).reverse().toString();
				writer.println(response);
			}
			
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

}
