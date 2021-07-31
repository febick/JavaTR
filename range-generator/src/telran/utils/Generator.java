package telran.utils;

public class Generator {

	private Rule rule;
	
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	
	public int[] generate(int number, int min, int max) {
		if (rule == null) {
			throw new IllegalArgumentException("You must set rule before use generate method.");
		}
		
		int[] res = new int[number];
		int counter = 0;
		
		while (counter != number) {
			int random = min + (int) (Math.random() * max);
			try {
				rule.check(random, min, max);
				res[counter] = random;
			} catch (NoRuleMatchException e) {
				int delta = e.getDelta();
				res[counter] = delta != 0 ? random + e.getDelta() : 0;
			}
			counter++;
		}
		
		return res;
	}
	
}
