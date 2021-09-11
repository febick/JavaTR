package telran.numbers.net;

import java.io.*;
import java.net.*;

import telran.numbers.CalculatorOperations;
import telran.view.EndOfInputException;

public class CalculatorTcpClient implements CalculatorOperations, Closeable {
	Socket socket;
	PrintStream writer;
	BufferedReader reader;

	public CalculatorTcpClient(String host, int port) throws Exception {
		socket = new Socket(host, port);
		writer = new PrintStream(socket.getOutputStream());
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	private int processRequest(String expression) {
		try {
			writer.println(expression);
			String response = reader.readLine();
			if (response == null) {
				throw new EndOfInputException();
			}
			try {
				int result = Integer.parseInt(response);
				return result;
			} catch (NumberFormatException e) {
				throw new RuntimeException(response);
			}

		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public int add(int op1, int op2) {

		return processRequest(getExpression(op1, op2, op2 < 0 ? "-" : "+"));
	}

	private String getExpression(int op1, int op2, String operator) {
		return firstNumberToString(op1) + operator + Math.abs(op2);
	}

	private String firstNumberToString(int op) {
		String base = op < 0 ? "0" : "";
		return base + op;
	}

	@Override
	public int subtract(int op1, int op2) {
		return processRequest(getExpression(op1, op2, op2 < 0 ? "+" : "-"));
	}

	@Override
	public int divide(int op1, int op2) {
		int res = processRequest(getExpression(op1, op2, "/"));
		
		return op2 < 0 ? -res : res;
	}

	@Override
	public int multiply(int op1, int op2) {
		int res =  processRequest(getExpression(op1, op2, "*"));
		return op2 < 0? -res : res;
	}

	@Override
	public int compute(String expression) {

		return processRequest(expression);
	}

	@Override
	public void close() throws IOException {
		socket.close();

	}

}
