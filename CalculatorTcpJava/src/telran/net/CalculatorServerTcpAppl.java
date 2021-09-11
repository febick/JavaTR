package telran.net;

public class CalculatorServerTcpAppl {

	public static void main(String[] args) {
		TcpJavaServer server = new TcpJavaServer(5000, new CalculatorProtocol());
		server.run();
	}

}
