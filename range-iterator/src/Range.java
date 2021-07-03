import java.util.Iterator;
import java.util.function.Predicate;

public class Range implements Iterable<Integer>  {

	private Integer array[];
	private Predicate<Integer> predicate = n -> true;

	public Range(int min, int max) throws InvalidArgumentException {
		if (min >= max) {
			throw new InvalidArgumentException("max can't be less or equal than min");
		} else {
			var countOfElements = max - min;
			array = new Integer[countOfElements];
			for (int i = 0; i < array.length; i++) {
				array[i] = min + i;
			}
		}
	}
		
	private class RangeIntegerIterator implements Iterator<Integer> {
		
		private int currentIndex = 0;
		
		@Override
		public boolean hasNext() {
			var res = false;
			while (currentIndex < array.length) {
				if (predicate.test(array[currentIndex])) {
					res = true;
					break;
				} else {
					currentIndex++;
				}
			}
			return res;
		}
		
		@Override
		public Integer next() {
			return array[currentIndex++];
		}
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new RangeIntegerIterator();
	}
	
	public void setPredicate(Predicate<Integer> predicate) {
		this.predicate = predicate;
	}
	
}

@SuppressWarnings("serial")
class InvalidArgumentException extends Exception{  
	InvalidArgumentException(String s){  
		super(s);  
	}  
} 
