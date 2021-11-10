import telran.performance.PerformanceTest;

public class CalculateSumPerformanceTest extends PerformanceTest {

	private static Runnable calculator;
	
	public CalculateSumPerformanceTest(String testName, int nRuns, Runnable calculator) {
		super(testName, nRuns);
		CalculateSumPerformanceTest.calculator = calculator;
	}

	@Override
	protected void runTest() {
		calculator.run();
	}
	
	

}
