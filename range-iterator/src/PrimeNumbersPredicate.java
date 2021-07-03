import java.util.function.Predicate;

public class PrimeNumbersPredicate implements Predicate<Integer> {

	@Override
	public boolean test(Integer t) {
		return isPrime(t);
	}
	
	private static boolean isPrime(int n) {
		if (n <= 1) return false;
		if (n == 2) return true;
		for (int i = 2; i * i <=n; ++i) {
			if(n % i == 0) return false;
		}
		return true;
	}


}
