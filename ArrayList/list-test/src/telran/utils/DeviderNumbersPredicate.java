package telran.utils;

import java.util.function.Predicate;

public class DeviderNumbersPredicate implements Predicate<Integer> {

	int divider;
	
	public DeviderNumbersPredicate(int divider) {
		this.divider = divider;
	}

	@Override
	public boolean test(Integer t) {
		return t % divider == 0;
	}

}
