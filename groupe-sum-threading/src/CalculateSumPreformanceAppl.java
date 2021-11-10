import telran.performance.PerformanceTest;

public class CalculateSumPreformanceAppl {

	private static final int N_RUNS = 1;
	private static final int N_GROUPS = 100_000;
	private static final int N_NUMBERS_GROUP = 10_000;
	private static final int[] TESTS = new int[] {1, 2, 4, 10, 40, 100, 200, 1000, 10000, 50000};

	public static void main(String[] args) throws InterruptedException {
		for (int thread_count: TESTS) {
			var testName = getTestName(thread_count, N_GROUPS, N_NUMBERS_GROUP);
			var calculator = new CalculateSumWithExecutor(thread_count, N_GROUPS, N_NUMBERS_GROUP);
			PerformanceTest pfTest = new CalculateSumPerformanceTest(testName, N_RUNS, calculator);
			pfTest.run();
		}
	}
	
	private static String getTestName(int n_threads, int n_groups, int n_numbers_group) {
		return String.format("Test with %d threads in pool (%d groups, %d numbers in one group)", n_threads, n_groups, n_numbers_group);
	}

}
