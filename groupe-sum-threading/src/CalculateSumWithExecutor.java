import java.util.*;
import java.util.concurrent.*;

public class CalculateSumWithExecutor extends CalculateSum implements Runnable {

	public CalculateSumWithExecutor(int n_threads, int n_groups, int n_number_group) {
		super(n_threads, n_groups, n_number_group);
	}
	
	@Override
	public void run() {
		int groups[][] = getGroups();
		ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);
		List<OneGroupSum> tasks = getTasks(executor, groups);
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(tasks.stream().mapToLong(OneGroupSum::getResult).sum());
	}
	
	private static int[][] getGroups() {
		ThreadLocalRandom tlr = ThreadLocalRandom.current();
		int result[][] = new int[N_GROUPS][N_NUMBERS_GROUP];
		for (int i = 0; i < N_GROUPS; i++) {
			for (int j = 0; j < N_NUMBERS_GROUP; j++) {
				result[i][j] = tlr.nextInt();
			}
		}
		return result;
	}

	
	private static List<OneGroupSum> getTasks(ExecutorService executor, int[][] groups) {
		List<OneGroupSum> result = new ArrayList<>();
		for (int i = 0; i < N_GROUPS; i++) {
			var group = new OneGroupSum(groups[i]); 
			result.add(group);
			executor.execute(group);
		}
		return result;
	}

}
