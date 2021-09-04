package telran.serverCalculator.service;

public class BasicCalculator {

	public static int calculate(String cleanExpression) {

		String[] numbers = cleanExpression.split("\\D+");
		String[] operators = cleanExpression.split("\\d+");

		int currentNumberIndex = 0;
		int sum = Integer.parseInt(numbers[currentNumberIndex]);
		int usedOperator = 1;

		while (usedOperator < operators.length) {
			var operator = operators[usedOperator++];
			var value = numbers[++currentNumberIndex];
			
			if (operator.equals("+")) {
				sum = add(sum, Integer.parseInt(value));
			} else if (operator.equals("-")) {
				sum = subtract(sum, Integer.parseInt(value));
			} else if (operator.equals("*")) {
				sum = multiply(sum, Integer.parseInt(value));
			} else if (operator.equals("/"))  {
				var secondValue = Integer.parseInt(value);
				sum = divide(sum, secondValue);
				if (sum == Integer.MAX_VALUE) { 
					throw new ArithmeticException("The amount received exceeds the maximum allowable value."); 
				}
			} else {
				throw new ArithmeticException("Used unknown operator");
			}
			
		}

		return sum;
	}
	
	private static int add(int op1, int op2) {
		return op1 + op2;
	}

	private static int subtract(int op1, int op2) {
		return op1 - op2;
	}

	private static int multiply(int op1, int op2) {
		return op1 * op2;
	}

	private static int divide(int op1, int op2) {
		if (op2 == 0) { throw new ArithmeticException("It is forbidden to divide by zero."); }
		return op1 / op2;
	}
	
	public static String arithemeticExpression() {
		var delimiter = "\\s*";
		var number = "\\d+";
		var operator = "[-+/*]";
		return String.format("%1$s%2$s(%1$s%3$s%1$s%2$s)*%1$s", delimiter, number, operator);
	}
	
}
