public interface ConnectionsPool extends Iterable<Connection> {
    /**
     * If connection exists, just moves the connection to the top,
     * else adds connection at first one the connections sequence.
     * If no room for new Connection the latest one will be removed.
     * O[1]
     * @param connection
     */
    void add(Connection connection);

    /**
     * If Connection exists, returns related Connection and
     * moves the Connection to the top.
     * If Connection doesn't exists, throws Exception.
     * O[1]
     * @param id
     * @return Connection
     */
    Connection get(int id) throws Exception;
}
