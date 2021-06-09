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
	
	@Override
	public boolean add(T obj, int index) {
		if (index >= 0 && index <= size) {
			
			T tmpArray[] = (T[]) new Object[array.length];
			System.arraycopy(array, 0, tmpArray, 0, index);
			System.arraycopy(array, index, tmpArray, index + 1, array.length - index - 1);
			tmpArray[index] = obj;
			array = tmpArray;
			
			size++;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public T get(int index) {
		if (index >= 0 && index <= size - 1) {
			return array[index];
		} else {
			return null;
		}
	}

	@Override
	public boolean remove(int index) {
		if (index >= 0 && index <= size - 1) {
			System.arraycopy(array, index + 1, array, index, array.length - index - 1);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int size() {
		return size;
	}

}