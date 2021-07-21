package telran.utils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TreeSet<T> implements SortedSet<T> {
	private static class Node<T> {
		T obj;
		Node<T> left;
		Node<T> right;
		Node<T> parent;
		Node (T obj) {
			this.obj = obj;
		}
	}
	private class TreeSetIterator implements Iterator<T> {
		Node<T> currentNode;
		Node<T> prevNode;
		
		public TreeSetIterator(Node<T> firstNode) {
			currentNode = getLeastFrom(firstNode);
		}
		
		public TreeSetIterator() {
			currentNode = getLeastFrom(root);
		}
		
		@Override
		public boolean hasNext() {
			return currentNode != null;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T res = currentNode.obj;
			prevNode = currentNode;
			updateCurrentNode();
			return res;
		}
		
		public Node<T> getCurrentNode() {
			return currentNode;
		}
		
		private void updateCurrentNode() {
			currentNode = currentNode.right != null ?
					getLeastFrom(currentNode.right) : getGreaterParent(currentNode);
		}

		private Node<T> getGreaterParent(Node<T> currentNode) {
			while(currentNode.parent != null &&
					currentNode.parent.left != currentNode) {
				currentNode = currentNode.parent;
			}
			return currentNode.parent;
		}

		@Override
		public void remove() {
			if (isJunction(prevNode)) { currentNode = prevNode; }
			removeNode(prevNode);
		}
	}
	
	private Node<T> root;
	private int size;
	private Comparator<T> comp;
	public TreeSet(Comparator<T> comp) {
		this.comp = comp;
	}

	@SuppressWarnings("unchecked")
	public TreeSet() {
		this((Comparator<T>)Comparator.naturalOrder());
	}
	
	@Override
	public Iterator<T> iterator() {
		return new TreeSetIterator();
	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public boolean add(T obj) {
		if (root == null) {
			root = new Node<>(obj);
		} else {
			Node<T> parent = getParent(obj);//returning null means the object exists
			if (parent == null) {
				return false;
			}
			Node<T> node = new Node<>(obj);
			if (comp.compare(obj, parent.obj) > 0) {
				parent.right = node;
			} else {
				parent.left = node;
			}
			node.parent = parent;

		}
		size++;
		return true;
	}

	private Node<T> getParent(T obj) {
		Node<T> current = root;
		Node<T> parent = null;
		while(current != null) {
			parent = current;
			int compRes = comp.compare(obj, current.obj);
			if (compRes == 0) {
				return null;
			}
			current = compRes > 0 ? current.right : current.left;
		}
		return parent;
	}
	@Override
	public boolean remove(T pattern) {
		Node<T> node = getNode(pattern);
		boolean res = false;
		if (node != null) {
			removeNode(node);
			res = true;
		}
		return res;
	}

	private void removeNode(Node<T> node) {
		size--;
		if (isJunction(node)) {
			removeJunction(node);
		} else {
			removeNonJunction(node);
		}

	}
	private void removeNonJunction(Node<T> node) {
		Node<T> child = node.left != null ? node.left : node.right;
		Node<T> parent = node.parent;
		if (child != null) {
			child.parent = parent;
		}
		if (parent == null) {
			root = child;

		} else if (parent.left == node) {
			parent.left = child;
		} else parent.right = child;


	}
	private void removeJunction(Node<T> node) {
		Node<T> substitute = getLeastFrom(node.right);
		node.obj = substitute.obj;
		removeNonJunction(substitute);

	}
	private Node<T> getLeastFrom(Node<T> node) {
		while(node.left != null) {
			node = node.left;
		}
		return node;
	}
	private boolean isJunction(Node<T> node) {

		return node.left != null && node.right != null;
	}
	private Node<T> getNode(T pattern) {
		int res = 0;
		Node<T> current = root;
		while(current != null &&
				(res = comp.compare(pattern, current.obj)) != 0) {
			current = res < 0 ? current.left : current.right;
		}
		return current;
	}

	@Override
	public T floor(T element) {
		Node<T> node = getNode(element);
		return node != null ? node.obj : find(element, true);
	}
	
	@Override
	public T ceiling(T element) {
		Node<T> node = getNode(element);
		return node != null ? node.obj : find(element, false);
	}
	
	private T find(T element, boolean isFloor) {
		var node = root;
		T current = null;

		while (node != null && !element.equals(current)) {
			if (comparing(node, element, isFloor)) {
				current = node.obj;
				node = isFloor ? node.right : node.left;
			} else {
				node = isFloor ? node.left : node.right;
			}
		}
		
		return current;
	}
	
	private boolean comparing(Node<T> node, T element, boolean isFloor) {
		if (isFloor) {
			return comp.compare(node.obj, element) <= 0;
		} else {
			return comp.compare(node.obj, element) >= 0;
		}
	}

	@Override
	public SortedSet<T> subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
		
		var firstNode = getNode(fromElement);
		
		if (firstNode == null) {
			System.out.println("fromElement isn't exist"); // TODO 
			return null;
		}
		
		SortedSet<T> res = new TreeSet<>(comp);
		var firstElement = firstNode.obj;
		T nextObject = null;
		var it = new TreeSetIterator(firstNode);
		
		if (fromInclusive) {
			res.add(firstElement);
		}
		
		while (it.hasNext()) {
			nextObject = it.next();
			if (comp.compare(nextObject, toElement) == -1) {
				res.add(nextObject);
			} else {
				break;
			}
		}
		
		if (toInclusive) {
			res.add(nextObject);
		}

		return res;
	}
	
	@Override
	public boolean contains(T pattern) {
		return root != null && getParent(pattern) == null;
	}

	@Override
	public void clean() {
		root = null;
		size = 0;
	}

	// Находим длину левого и правого дерева, к большему прибаляем едницу и возвращаем
	public Integer height() {
		return height(root);
	}

	private Integer height(Node<T> nodeRoot) {
		int res = 0;
		if (nodeRoot != null) {
			int heightLeft = height(nodeRoot.left);
			int heightRight = height(nodeRoot.right);
			res = Math.max(heightLeft, heightRight) + 1;
		}
		return res;
	}

	public Integer width() {
		return width(root);
	}

	private Integer width(Node<T> nodeRoot) {
		if (nodeRoot == null) { return 0; }
		if (nodeRoot.left == null && nodeRoot.right == null) {return 1; } 
		int widthLeft = width(nodeRoot.left);
		int widthRight = width(nodeRoot.right);
		return widthLeft + widthRight;
	}

	
	public void balance() {
		var ar = getSortedNodeArray();
		root = getRoot(ar, 0, size - 1, null);
	}
	
	@SuppressWarnings("unchecked")
	private Node<T>[] getSortedNodeArray() {
		Node<T> ar[] = new Node[size()];
		var it = new TreeSetIterator();
		var index = 0;
		while (it.hasNext()) {
			ar[index++] = it.getCurrentNode();
			it.next();
		}
		return ar;
	}
	
	private Node<T> getRoot(Node<T>[] ar, int left, int right, Node<T> parent) {
		if (left > right) { return null; }
		var middle = (left + right) / 2;
		var newRoot = ar[middle];
		newRoot.parent = parent;
		newRoot.left = getRoot(ar, left, middle - 1, newRoot);
		newRoot.right = getRoot(ar, middle + 1, right, newRoot);
		return newRoot; 
	}
	
	

}
