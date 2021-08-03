package telran.text;

public class BasicCalculator {

	public static int calculate(String expression) {

		if (!expression.matches(StringRegularExpression.arithemeticExpression())) {
			throw new IllegalArgumentException();
		}

		String cleanExpression = expression.replaceAll("\\s", "");
		String[] numbers = cleanExpression.trim().split("\\D+");
		String[] operators = cleanExpression.trim().split("\\d+");

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
				sum = divide(sum, Integer.parseInt(value));
				if (sum == Integer.MAX_VALUE) { break; }
			}
			
		}

		return sum;
	}


	public static int add(int op1, int op2) {
		return op1 + op2;
	}

	public static int subtract(int op1, int op2) {
		return op1 - op2;
	}

	public static int multiply(int op1, int op2) {
		return op1 * op2;
	}

	public static int divide(int op1, int op2) {
		if (op2 == 0) { return Integer.MAX_VALUE; }
		return op1 / op2;
	}

}
