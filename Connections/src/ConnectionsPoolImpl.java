import java.util.*;

public class ConnectionsPoolImpl implements ConnectionsPool {

	private HashMap<Integer, Node> map;
	private ConnectionsList connList;
	private int size;
	private int limit;

	public ConnectionsPoolImpl(int limit) {
		this.connList = new ConnectionsList();
		this.limit = limit;
		this.map = new HashMap<>();
		this.size = 0;
	}

	private static class Node {
		public Connection connection;
		public Node next;
		public Node prev;

		public Node(Connection connection) {
			this.connection = connection;
		}
	}

	private static class ConnectionsList {
		private Node head;
		private Node tail;

		Node addNode(Connection connection) {
			Node node = new Node(connection);
			addFirst(node);
			return node;
		}

		private void addFirst(Node node) {
			if (head == null) {
				head = tail = node;
			} else {
				node.next = head;
				node.prev = null;
				head.prev = node;
				head = node;
			}
		}

		Node removeTail() {
			Node res = tail;
			tail.prev.next = null;
			tail = tail.prev;
			return res;
		}

		void moveNode(Node node) {
			if (node != head) {
				removeNode(node);
				addFirst(node);
			}
		}

		private void removeNode(Node node) {
			if (node == tail) {
				removeTail();
			} else {
				node.prev.next = node.next;
				node.next.prev = node.prev;
			}
		}

		public Node getHead() {
			return head;
		}
	}

	@Override
	public Iterator<Connection> iterator() {
		return new Iterator<Connection>() {
			private Node current = connList.getHead();
			@Override
			public boolean hasNext() {
				return current != null;
			}
			@Override
			public Connection next() {
				Connection res = current.connection;
				current = current.next;
				return res;
			}
		};
	}

	@Override
	public void add(Connection connection) {
		Node node = map.get(connection.getId());
		if (node == null) {
			addNewConnection(connection);
		} else {
			moveExistingConnection(node);
		}
	}

	private void moveExistingConnection(Node node) {
			connList.moveNode(node);
	}

	private void addNewConnection(Connection connection) {
		if (size == limit) {
			map.remove(connList.removeTail().connection.getId());
		} else {	
			size++;
		}
		Node node = connList.addNode(connection);
		map.put(connection.getId(), node);
	}

	@Override
	public Connection get(int id) throws NoSuchElementException {
		Node node = map.get(id);
		if (node == null) {
			throw new NoSuchElementException("Connection with ID = " + id + " not found.");
		}
		connList.moveNode(node);
		return node.connection;
	}

}
