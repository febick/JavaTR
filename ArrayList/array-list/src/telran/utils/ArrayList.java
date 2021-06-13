package telran.utils;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class ArrayList<T> implements List<T> {
	
	private static final int DEFAULT_CAPACITY = 16;
	private T array[];
	int size = 0;

	public ArrayList(int capacity) {
		array = (T[]) new Object[capacity];
	}
	
	public ArrayList() {
		this(DEFAULT_CAPACITY);
	}
	
	@Override
	public void add(T obj) {
		if (size >= array.length) {
			allocate();
		} 
		array[size++] = obj;
	}

	private void allocate() {
		array = Arrays.copyOf(array, array.length * 2);
	}
	
	private boolean inRange(int index) {
		return (index >= 0 && index <= size) ? true : false;
	}
	
	@Override
	public boolean add(T obj, int index) {
		boolean res = false;
		if (inRange(index)) {
			System.arraycopy(array, index, array, index + 1, size - 1); 
			array[index] = obj;
			size++;			
			res = true;
		} 
		return res;
	}

	@Override
	public T get(int index) {
		return inRange(index) ? array[index] : null;
	}

	@Override
	public boolean remove(int index) {
		boolean res = false;
		if (inRange(index)) {
			size--;
			System.arraycopy(array, index + 1, array, index, size - index);
			array[size] = null; // fixing memory leaks
			res = true;
		}
		return res;
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public int indexOf(T pattern) {
		int index = 0;
		while (index < size && !array[index].equals(pattern)) {
			index++;
		}
		return index < size ? index : -1;
	}

	@Override
	public int lastIndexOf(T pattern) {
		int index = size - 1;
		while (index > 0 && !array[index].equals(pattern)) {
			index--;
		}
		return index < size ? index : -1;
	}

	@Override
	public boolean remove(T pattern) {
		boolean res = false;
		int index = indexOf(pattern);
		if (index != -1) {
			remove(index);
			res = true;
		}
		return res;
	}

	@Override
	public void addAll(List<T> objects) {
		for (int i = 0; i < objects.size(); i++) {
			add(objects.get(i));
		}
	}

	@Override
	public boolean removeAll(List<T> patterns) {
		boolean res = false;
		for (int i = 0; i < patterns.size(); i++) {
			for (int j = 0; j < size; j++) {
				if (array[j].equals(patterns.get(i))) {
					remove(j);
					res = true;
				}
			}
		}
		return res;
	}

	@Override
	public boolean retainAll(List<T> patterns) {
		return removeAll(makeInverse(array, patterns));
	}

	private List<T> makeInverse(T[] array, List<T> patterns) {
		var res = new ArrayList<T>();
		for (int i = 0; i < size; i++) {
			var obj = get(i);
			if (patterns.indexOf(obj) == -1 ) {
				res.add(obj);
			}
		}
		return res;
	}

	@Override
	public T set(T object, int index) {
		T res = null;
		if (inRange(index))	{
			array[index] = object;
			res = array[index];
		} 
		return res;
	}

	@Override
	public boolean swap(int index1, int index2) {
		boolean res = false;
		if (inRange(index1) && inRange(index2)) {
			T tmpObj = array[index1];
			array[index1] = array[index2];
			array[index2] = tmpObj;
			res = true;
		}
		return res;
	}

}