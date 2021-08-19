import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConnectionsPoolImpl implements ConnectionsPool {

    private ConnectionsList connectionsList = new ConnectionsList(10);

    private static class Node {
        public Connection connection;
        public Node next;
        public Node prev;
        public Node(Connection connection) {
            this.connection = connection;
        }
    }

    private static class ConnectionsList {
        HashMap<Integer, Node> map = new HashMap<>();
        Node head;
        Node tail;
        int size;
        int limit;
        ConnectionsList(int limit) {
            this.limit = limit;
        }

        public void add(Connection connection) {
            if (size > limit) { removeTail(); }
            Node current = map.get(connection.getId());
            if (current != null) {
                moveToTop(current);
            } else {
                Node newNode = new Node(connection);
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
                size++;
            }
         }

         public Connection get(int id) {
            return map.get(id).connection;
         }

        public void removeTail() {
            map.remove(tail.connection.getId());
            tail.prev.next = null;
            tail = tail.prev;
            size--;
        }

        private void moveToTop(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;
            node.next = head;
            head.prev = node;
            head = node;
        }

    }

    @Override
    public void add(Connection connection) {
        connectionsList.add(connection);
    }

    @Override
    public Connection get(int id) throws Exception {
        Connection res = connectionsList.get(id);
        if (res == null) { throw new NoSuchElementException(); }
        return res;
    }

    @Override
    public Iterator<Connection> iterator() {
        return null;
    }
}
