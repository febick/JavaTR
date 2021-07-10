package telran.utils;

import java.util.Comparator;
import java.util.Iterator;

public class TreeSet<T> implements Set<T> {

	private int size;
	private Node<T> root;
	private Comparator<T> comp;
	
	public TreeSet(Comparator<T> comp) {
		this.comp = comp;
	}
	
	@SuppressWarnings("unchecked")
	public TreeSet() {
		this((Comparator<T>)Comparator.naturalOrder());
	}
	
	private static class Node<T> {
		T obj;
		Node<T> left;
		Node<T> rigth;
		Node<T> parrent;
		public Node (T obj) {
			this.obj = obj;
		}
	}
	
	public Node<T> getMinNodeFrom(Node<T> node) {
		Node<T> res = node;
		while (res.left != null) {
			res = res.left;
		}
		
		if (res.rigth != null) {
			res = res.rigth;
		}
		
		return res;
	}

	
	private class TreeSetIterator implements Iterator<T> {

		Node<T> current = root;
		Node<T> prev = current;
		
		public TreeSetIterator() {
			current = getMinNodeFrom(root);
		}
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			prev = current;
			
			if (current.rigth != null) {
				current = getMinNodeFrom(current.rigth);
			} else {
				current = getLeftParrent(current);
			}
			
			return prev != current ? prev.obj : null;
			
		}
		
		@Override
		public void remove() {
			TreeSet.this.removeNode(prev);
		}

		private Node<T> getLeftParrent(Node<T> node) {
			while (node.parrent != null && node.parrent.left != node) {
				node = node.parrent;
			}
			return node.parrent;
		}
		
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
			Node<T> parrent = getParrent(obj); // if null means obj exist
			if (parrent == null) { 
				return false; 
			}
			Node<T> node = new Node<>(obj);
			if (comp.compare(obj, parrent.obj) > 0) {
				parrent.rigth = node;
			} else {
				parrent.left = node;
			}
			node.parrent = parrent;
		}
		size++;
		return true;
	}

	private Node<T> getParrent(T obj) {
		Node<T> current = root;
		Node<T> parrent = null;
		while (current != null) {
			parrent = current;
			int compRes = comp.compare(obj, current.obj);
			if (compRes == 0) {
				return null;
			}
			current = compRes > 0 ? current.rigth : current.left;
		}
		return parrent;
	}

	@Override
	public boolean remove(T pattern) {
		if (contains(pattern)) {
			return removeNode(getNode(pattern));
		} else {
			return false;
		}
	}
	
	private Node<T> getNode(T obj) {
		Node<T> res = root;
		while (res != null) {
			int compRes = comp.compare(obj, res.obj);
			if (compRes == 0) {
				return res;
			}
			res = compRes > 0 ? res.rigth : res.left;
		}
		return null;
	}
	
	private boolean isJunction(Node<T> node) {
		return (node.left != null || node.rigth != null) && node.parrent != null;
	}
	
	private boolean isTail(Node<T> node) {
		return node.left == null && node.rigth == null;
	}
	
	private boolean isRoot(Node<T> node) {
		return node.parrent == null;
	}

	private boolean removeNode(Node<T> nodeToRemove) {
		boolean res = false;
		
		if (isTail(nodeToRemove)) {
			detach(nodeToRemove);
			res = true;
		}
		
		if (isJunction(nodeToRemove)) {
			Node<T> minNode = null;

			if (nodeToRemove.left != null) {
				minNode = getMinNodeFrom(nodeToRemove.left);
			} else {
				minNode = getMinNodeFrom(nodeToRemove.rigth);
			}
			
			nodeToRemove.obj = minNode.obj;
			detach(minNode);
			res = true;
		}
		
		if (isRoot(nodeToRemove)) {
			Node<T> newRoot = getMinNodeFrom(nodeToRemove.rigth);
			nodeToRemove.obj = newRoot.obj;
			detach(newRoot);
			res = true;
		}
		
		return res;
	}

	private void detach(Node<T> node) {
		if (node.parrent.left == node) {
			node.parrent.left = null;
		} else {
			node.parrent.rigth = null;
		}
		size--;
	}

	@Override
	public boolean contains(T pattern) {
		return root != null && getParrent(pattern) == null;
	}

	@Override
	public void clean() {
		root = null;
		size = 0;
	}

}
