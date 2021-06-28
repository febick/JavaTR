package telran.utils;

import java.util.Comparator;
import java.util.function.Predicate;


public interface List<T> {
	/**
	 * 
	 * @return number of elements
	 */
	int size();
	/**
	 * adding obj at the end
	 * 
	 * @param obj
	 */
	void add(T obj);

	/**
	 * 
	 * @param obj
	 * @param index [0, size]
	 * @return true if added, otherwise false (wrong index)
	 */
	boolean add(T obj, int index); 

	/**
	 * 
	 * @param index [0, size -1]
	 * @return reference to T object; if a given index is wrong -> null
	 */
	T get(int index);

	/**
	 * removes object at a given index
	 * @param index [0, size - 1]
	 * @return true if removed, otherwise false (wrong index)
	 */
	boolean remove(int index);
	
	/**
	 * 
	 * @param pattern
	 * @return index of first occurrence for an object equaled to the pattern
	 * in the case no object equaled to the pattern returns -1
	 */
	default int indexOf(T pattern) {
		return indexOf(n -> n.equals(pattern));
	}
	
	/**nb
	 * 
	 * @param pattern
	 * @return index of the last occurrence for an object equaled to the pattern
	 * in the case no object equaled to the pattern, returns -1
	 */
	default int lastIndexOf(T pattern) {
		return lastIndexOf(n -> n.equals(pattern));
	}
	
	/**
	 * 
	 * @param predcate
	 * @return index (first object matching predicate) or -1
	 */
	int indexOf(Predicate<T> predcate);
	
	/**
	 * 
	 * @param predcate
	 * @return index (last object matching predicate) or -1
	 */
	int lastIndexOf(Predicate<T> predcate);
	
	/**
	 * removing all objects matching a given predicate
	 * @param predicate
	 * @return true if at least one object has been removed
	 */
	boolean removeIf(Predicate<T> predicate);
	
	/**
	 * for several equaled objects to leave only one and remove others 
	 * @return true if at least one object has been removed
	 */
	default boolean removeRepeated() {
		return removeIf(n -> indexOf(n) != lastIndexOf(n));
	}
	
	/**
     * Return count of object in list
     * @param pattern
     * @return count of object
     */
    default int count(T pattern) {
    	var size = size();
    	var count = 0;
    	for (int i = 0; i < size; i++) {
    		var current = get(i);
    		if (current.equals(pattern)) {
    			count++;
    		}
    	}
    	return count;
    }
	
	/**
	 * remove all elements from list
	 */
	default void clean() {
		removeAll(this);
	}
	
	/**
	 * removes first occurred object equaled to a given pattern
	 * @param pattern
	 * @return true if removed otherwise false
	 * (here there is some challenge, try to understand it)
	 */
	default boolean remove(T pattern) {
		return remove(indexOf(pattern));
	};
	
	/**
	 * Adds all objects
	 * @param objects
	 */
	default void addAll(List<T> objects) {
		int currnetSize = objects.size();
		for (int i = 0; i < currnetSize; i++) {
			add(objects.get(i));
		}
	};
	
	/**
	 * removes all objects equaled to the given patterns
	 * @param patterns
	 * @return true if at least one object has been removed
	 */
	default  boolean removeAll (List<T> patterns) {
		
		if (patterns.equals(this)) {
			clean();
			return true;
		}
		
		return removeIf(obj -> patterns.indexOf(obj) >= 0);
	}
	
	/**
	 * removes all objects not equaled to the given patterns
	 * @param patterns
	 * @return true if at least one object has been removed
	 */
	default boolean retainAll (List<T> patterns) {
		return removeIf(obj -> patterns.indexOf(obj) < 0);
	}
	
	/**
	 * sets new reference to an object at existing index
	 * @param object
	 * @param index
	 * @return reference to an old object at the index or null in the case of wrong index
	 */
	T set(T object, int index);
	
	/**
	 * swaps objects at the given indexes
	 * @param index1
	 * @param index2
	 * @return return true if swapped, false in the case of any wrong index
	 */
	boolean swap(int index1, int index2);

	default boolean contains(T pattern) {
		return indexOf(pattern) >= 0;
 	}
	
	static <T> T max(List<T> list, Comparator<T> comp) {
		T max = list.get(0);
		int size = list.size();
		
		for (int i = 1; i < size; i++) {
			T current = list.get(i);
			if (comp.compare(max, current) < 0) {
				max = current;
			}
		}

		return max;
	}
	
	@SuppressWarnings("unchecked")
	static <T> T max(List<T> list) {
		return max(list, (Comparator<T>) Comparator.naturalOrder());
	}
	
	static <T> T min(List<T> list, Comparator<T> comp) {
		return max(list, comp.reversed());
	}
	
	@SuppressWarnings("unchecked")
	default void sort() {
		sort((Comparator<T>) Comparator.naturalOrder());
	}
	
	default void sort(Comparator<T> comp) {
		boolean isListSorted = false;
		int maxIterations = size();
		while (!isListSorted) {
			isListSorted = true;
			maxIterations--;
			for (int i = 0; i < maxIterations; i++) {
				if (comp.compare(get(i), get(i + 1)) > 0) {
					isListSorted = false;
					swap(i, i + 1);
				}	
			}
		}
	}


}