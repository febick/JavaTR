package telran.numbers.net;

import telran.net.TcpJavaClient;
import telran.numbers.CalculatorOperations;

public class CalculatorOperationsTcpJava extends TcpJavaClient implements CalculatorOperations {

	public CalculatorOperationsTcpJava(String host, int port) throws Exception {
		super(host, port);
	}

	@Override
	public int add(int op1, int op2) {
		var request = getExpression(op1, op2, op2 < 0 ? "-" : "+");
		return (int) getResponse(request);
	}

	@Override
	public int subtract(int op1, int op2) {
		var request = getExpression(op1, op2, op2 < 0 ? "+" : "-");
		return (int) getResponse(request);
	}

	@Override
	public int divide(int op1, int op2) {
		var request = getExpression(op1, op2, "/");
		var response = (int) getResponse(request);
		return op2 < 0 ?  -response : response;
	}

	@Override
	public int multiply(int op1, int op2) {
		var request = getExpression(op1, op2, "*");
		var response = (int) getResponse(request);
		return op2 < 0? -response : response;
	}

	@Override
	public int compute(String expression) {
		var response = (int) getResponse(expression);
		return response;
	}
	
	private int getResponse(String request) {
		try {
			var response = send("calculate", request);
			return (int) response;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private String getExpression(int op1, int op2, String operator) {
		return firstNumberToString(op1) + operator + Math.abs(op2);
	}

	private String firstNumberToString(int op) {
		String base = op < 0 ? "0" : "";
		return base + op;
	}

}
