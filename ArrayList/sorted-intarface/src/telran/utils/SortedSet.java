package telran.utils;

public interface SortedSet<T> extends Set<T>{
	T ceiling(T element);
	T floor (T element);
	SortedSet<T> subSet(T fromElement, boolean fromInclusive,
			T toElement, boolean toInclusive);
}
