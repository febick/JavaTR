import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConnectionsPoolImpl implements ConnectionsPool {

    private static int limit;
    private static int size;
    private static HashMap<Integer, Node> map;
    private final ConnectionsList connectionsList;

    public ConnectionsPoolImpl(int limit) {
        this.limit = limit;
        this.map = new HashMap<>();
        this.connectionsList = new ConnectionsList();
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
        Node head;
        Node tail;

        public void add(Connection connection) {
            Node newNode = new Node(connection);
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            map.put(connection.getId(), newNode);
            size++;
        }

        public void removeTail() {
            map.remove(tail.connection.getId());
            tail.prev.next = null;
            tail = tail.prev;
            size--;
        }

        private void moveToTop(Node node) {
            if (head != node) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                node.prev = null;
                node.next = head;
                head.prev = node;
                head = node;
            }
        }

    }

    @Override
    public void add(Connection connection) {
        if (size > limit) { connectionsList.removeTail(); }

        Node current = map.get(connection.getId());
        if (current != null) {
            connectionsList.moveToTop(current);
        } else {
            connectionsList.add(connection);
        }
    }

    @Override
    public Connection get(int id) throws NoSuchElementException {
        Connection res = map.get(id).connection;
        if (res == null) { throw new NoSuchElementException(); }
        return res;
    }

    @Override
    public Iterator<Connection> iterator() {
        return new Iterator<Connection>() {
            Node current = connectionsList.head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Connection next() {
                Node res = current;
                current = current.next;
                return res.connection;
            }
        };
    }
}
