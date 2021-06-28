package telran.utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class LinkedList<T> extends AbstractList<T> {
	
	static private class Node<T> {
		public T obj;
		public Node<T> next;
		public Node<T> prev;
		public Node(T obj) {
			this.obj = obj;
		}
	}
	
	private Node<T> head;
	private Node<T> tail;

	@Override
	public void add(T obj) {
		Node<T> node = new Node<>(obj);
		
		if (tail != null) {
			tail.next = node;
			node.prev = tail;
			tail = node;
		} else {
			head = tail = node;
		}
		
		size++;
	}
	
	@Override
	public boolean add(T obj, int index) {
		
		if (index == size) {
			add(obj);
			return true;
		}
		
		if (!inRange(index)) {
			return false;
		}
		
		var newNode = new Node<T>(obj);
		
		if (index == 0) {
			head.prev = newNode;
			newNode.next = head;
			head = newNode;
		} else {
			var rightNode = getNodeAtIndex(index);
			var leftNode = rightNode.prev;
			
			leftNode.next = newNode;
			rightNode.prev = newNode;
			newNode.prev = leftNode;
			newNode.next = rightNode;
		}
		
		size++;
		return true;
		
	}

	@Override
	public T get(int index) {
		return inRange(index) ? getNodeAtIndex(index).obj : null;
	}

	@Override
	public boolean remove(int index) {
		if (!inRange(index)) {
			return false;
		} else {
			removeNode(getNodeAtIndex(index));
			return true;
		}
	}

	@Override
	public int indexOf(Predicate<T> predcate) {
		int index = 0;
		var current = head;
		while (index < size && !predcate.test(current.obj)) {
			current = current.next;
			index++;
		}
		return index < size ? index : -1;
	}

	@Override
	public int lastIndexOf(Predicate<T> predcate) {
		var index = size - 1;
		var current = tail;
		while (index >= 0 && !predcate.test(current.obj)) {
			current = current.prev;
			index--;
		}
		return index < size ? index : -1;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		var sizeBefore = size;
		var current = head;
		while (current != null) {
			if (predicate.test(current.obj)) {				
				removeNode(current);				
			}
			current = current.next;
		}
		return sizeBefore > size;
	}

	@Override
	public T set(T object, int index) {
		T res = null;
		if (inRange(index))	{
			res = getNodeAtIndex(index).obj;
			getNodeAtIndex(index).obj = object;
		} 
		return res;
	}

	@Override
	public boolean swap(int index1, int index2) {
		
		if (!inRange(index1) || !inRange(index2) || index1 == index2) {
			return false;			
		}
		
		var firstNode = getNodeAtIndex(index1);
		var secondNode = getNodeAtIndex(index2);
		
		var tmpObj = firstNode.obj;
		firstNode.obj = secondNode.obj;
		secondNode.obj = tmpObj;
		
		return true;
	}
	
	@Override
	public void clean() {
		head = tail = null;
		size = 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void sort(Comparator<T> comp) {
		var array = (T[]) new Object[size];
		var current = head;		
		for (int i = 0; i < size; i++) {
			array[i] = current.obj;
			current = current.next;
		}
		
		Arrays.sort(array, comp);
		current = head;
		
		for (int i = 0; i < size; i++) {
			current.obj = array[i];
			current = current.next;
		}		
		
		array = null;
	}
	
	private Node<T> getNodeAtIndex(int index) {
		return index < size / 2 ? getNodeLtr(index) : getNodeRtl(index);
	}
	
	private Node<T> getNodeLtr(int index) {
		Node<T> tmp = head;
		for (int i = 0; i < index; i++) {
			tmp = tmp.next;
		}
		return tmp;
	}

	private Node<T> getNodeRtl(int index) {
		Node<T> tmp = tail;
		for (int i = size - 1; i > index; i--) {
			tmp = tmp.prev;
		}
		return tmp;
	}
		
	private void removeNode(Node<T> node) {

		if (head == tail) {
			head = tail = null;
			size = 0;
		} else if (node == head) {
			removeHead();
		} else if (node == tail) {
			removeTail();
		} else {
			node.prev.next = node.next;
			node.next.prev = node.prev;
		}

		size--;
	}

	private void removeTail() {
		tail.prev.next = null;
		tail = tail.prev;
	}

	private void removeHead() {
		head.next.prev = null;
		head = head.next;
	}

}
