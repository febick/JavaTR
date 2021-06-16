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
		return index >= 0 && index < size;
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
		while (index >= 0 && !array[index].equals(pattern)) {
			index--;
		}
		return index >= 0 ? index : -1;
	}

	@Override
	public boolean remove(T pattern) {
		return remove(indexOf(pattern));
	}

	@Override
	public void addAll(List<T> objects) {
		int size = objects.size();
		for (int i = 0; i < size; i++) {
			add(objects.get(i));
		}
	}

	@Override
	public boolean removeAll(List<T> patterns) {
		boolean isRetain = false;
		boolean res = false;
		if (this == patterns) {
			size = 0;
			clean(0, size);
			res = true;
		} else {
			res =  removing(patterns, isRetain);
		}
		return res;
		
	}

	private void clean(int startIndex, int sizeBefore) {
		for (int i = startIndex; i < sizeBefore; i++) {
			array[i] = null;
		}
		
	}

	private boolean removing(List<T> patterns, boolean isRetain) {
		int sizeBeforeRemoving = size;
		int indexAfterRemoving = 0;
		for(int i = 0; i < sizeBeforeRemoving; i++) {
			T current = array[i];
			if (conditionRemoving(patterns, current, isRetain)) {
				size--;
			} else {
				array[indexAfterRemoving++] = array[i];
			}
		}
		boolean res = sizeBeforeRemoving > size;
		if (res) {
			clean(size, sizeBeforeRemoving);
		}
		return res;
	}

	private boolean conditionRemoving(List<T> patterns, T current,
			boolean isRetain) {
		boolean res = patterns.indexOf(current) >= 0;
		return isRetain ? !res : res;
	}
	
	@Override
	public boolean retainAll(List<T> patterns) {
		boolean isRetain = true;
		return this == patterns ? false : removing(patterns, isRetain);
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
	

}