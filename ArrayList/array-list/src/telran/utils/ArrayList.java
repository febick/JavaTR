package telran.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class ArrayList<T> extends AbstractList<T> {
	
	private static final int DEFAULT_CAPACITY = 16;
	private T array[];

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
		if (index < 0 || index > size) {
			return false;
		}
		if (size >= array.length) {
			allocate();
		}
		System.arraycopy(array, index, array, index + 1, size - index);
		array[index] = obj;
		size++;
		return true;
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
	public int indexOf(Predicate<T> predcate) {
		int index = 0;
		while (index < size && !predcate.test(array[index])) {
			index++;
		}
		return index < size ? index : -1;
	}
	
	@Override
	public int lastIndexOf(Predicate<T> predcate) {
		int index = size -1;
		while (index >= 0 && !predcate.test(array[index])) {
			index--;
		}
		return index;
	}
	
	@Override
	public boolean removeIf(Predicate<T> predicate) {
		int countOfElements = size;
		for (int i = countOfElements - 1; i >= 0; i--) {
			if (predicate.test(array[i])) {
				remove(i);
			} 
		}
		return countOfElements > size;
	}

	@Override
	public T set(T object, int index) {
		T res = null;
		if (inRange(index))	{
			res = array[index];
			array[index] = object;
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

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}