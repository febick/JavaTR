package telran.utils;

public class DividerRule implements Rule {

	int divider;
	private int min;
	private int max;
	
	public DividerRule(int divider) {
		this.divider = divider;
	}
	
	@Override
	public void check(int number, int min, int max) throws NoRuleMatchException {
		if (min >= max) { throw new IllegalArgumentException("Min can't be greater or equal than max.");}
		this.min = min;
		this.max = max;
		
		if (checkDevider(number) && isInRange(number)) {
			System.out.println(String.format("Number %d matches the rule and it is in range [%d-%d]", number, min, max));
		} else {
			throw new NoRuleMatchException(findNearestDelta(number));
		}
	}
	
	private int findNearestDelta(int number) {
		int delta = 0, leftDelta = 0, rightDelta = 0;
		int leftValue = number - 1;
		int rightValue = number + 1;
		
		while (delta == 0) {
			leftDelta = 0; rightDelta = 0;
			
			if (checkDevider(leftValue) && isInRange(leftValue)) {
				int tmpLeft = number - leftValue;
				leftDelta = tmpLeft >= min ? tmpLeft : 0 ;
			}
			
			if (checkDevider(rightValue) && isInRange(rightValue)) {
				int tmpRight = rightValue - number;
				rightDelta = tmpRight <= max ? tmpRight : 0;
			}

			if (leftDelta != 0 && rightDelta != 0) {
				delta = leftDelta < rightDelta ? -leftDelta : rightDelta;
			} else if (leftDelta != 0 && rightDelta == 0) {
				delta = -leftDelta;
			} else if (leftDelta == 0 && rightDelta != 0) {
				delta = rightDelta;
			} else {
				leftValue--;
				rightValue++;
			}
		}
		
		return delta;
	}
	
	private boolean checkDevider(int number) {
		return number % divider == 0 ? true : false;
	}
	
	private boolean isInRange(int number) {
		return number >= min && number <= max ? true : false;
	}

}
