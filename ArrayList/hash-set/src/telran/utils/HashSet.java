package telran.utils;

import java.util.Iterator;


public class HashSet<T> implements Set<T> {

	private static final int DEFAULT_TABLE_LENGHT = 16;
	private int size;
	private LinkedList<T>[] hashTable;
	private double factor = 0.75;

	@SuppressWarnings("unchecked")
	public HashSet(int initialTableLenght) {
		hashTable = new LinkedList[initialTableLenght];
	}

	public HashSet() {
		this(DEFAULT_TABLE_LENGHT);
	}
	
	private class HashSetIterator implements Iterator<T> {

		private int currentIndex = 0;
		private Iterator<T> currentIterator;
		private T prevObject;
		
		public HashSetIterator() {
			currentIterator = getNextList();
		}
		
		private Iterator<T> getNextList() {
			while (currentIndex < hashTable.length) {
				var currentList = hashTable[currentIndex];
				if (currentList == null || !currentList.iterator().hasNext()) {					
					currentIndex++;
				} else {					
					return currentList.iterator();
				}
			}
			return null;
		}

		@Override
		public boolean hasNext() {
			return currentIterator != null;
		}

		@Override
		public T next() {
			T res = null;
			if (currentIterator != null) {
				prevObject = currentIterator.next();
				if (!currentIterator.hasNext()) {
					currentIndex++;
					currentIterator = getNextList();
				}
				res = prevObject;
			} 
			return res;
		}
		
		@Override
		public void remove() {
			HashSet.this.remove(prevObject);
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new HashSetIterator();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean add(T obj) {
		boolean res = false;

		if (size >= hashTable.length * factor) {
			recreateTable();
		}

		int index = getIndex(obj);
			
		if (hashTable[index] == null) {
			hashTable[index] = new LinkedList<>();
		}

		if (!hashTable[index].contains(obj)) {
			hashTable[index].add(obj);
			size++;
			res = true;
		}

		return res;
	}

	private int getIndex(T obj) {
		int hashCode = obj.hashCode();
		return Math.abs(hashCode) % hashTable.length;
	}

	private void recreateTable() {
		HashSet<T> tmp = new HashSet<>(hashTable.length * 2);
		for (LinkedList<T> list : hashTable) {
			if (list != null) {
				for (T obj : list) {
					tmp.add(obj);
				}
			}
		}
		hashTable = tmp.hashTable;
	}

	@Override
	public boolean remove(T pattern) {
		boolean res = false;
		var index = getIndex(pattern);
		var currentList = hashTable[index];
		if (currentList != null) {
			res = currentList.remove(pattern);
		}
		if (res) {
			size--;
		}
		return res;
	}

	@Override
	public boolean contains(T pattern) {
		int index = getIndex(pattern);
		return hashTable[index] != null && hashTable[index].contains(pattern);
	}

	@SuppressWarnings("unused")
	@Override
	public void clean() {
		for (LinkedList<T> list : hashTable) {
			list = null;
		}
		size = 0;
	}

}
