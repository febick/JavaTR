import java.util.Arrays;

public class OneGroupSum implements Runnable {

	int group[];
	long result;
	
	public OneGroupSum(int[] group) {
		super();
		this.group = group;
	}

	@Override
	public void run() {
		result = Arrays.stream(group).asLongStream().sum();
	}

	public long getResult() {
		return result;
	}

}
