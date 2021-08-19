public class Connection {
    private int id;
    private String ipAddress;
    private int port;

    Connection (int id, String ipAddress, int port) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.port = port;
    }
    public int getId() {
        return id;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
