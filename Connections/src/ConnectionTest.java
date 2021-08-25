import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConnectionsPoolTest {
  
  ConnectionsPoolImpl pool;
  Connection con1 = new Connection(1, "192.168.0.10", 1020);
  Connection con2 = new Connection(2, "192.168.1.10", 2000);
  Connection con3 = new Connection(3, "192.168.0.100", 2020);
  Connection con4 = new Connection(4, "192.168.10.15", 5020);
  Connection con5 = new Connection(5, "192.168.20.20", 1111);
  
  Connection [] arrayConn = {con1, con2, con3, con4, con5};

  @BeforeEach
  void setUp() throws Exception {
    pool = new ConnectionsPoolImpl(5);
  }

  @Test
  void testAddNoSuchElement() {
    Connection con = new Connection(1, "192.168.0.10", 1020);
    try {
      pool.get(con.getId());
      fail();
    } catch (NoSuchElementException e) {
      System.out.println(e);
    }  
  }
  
  @Test
  void testAdd() {
    Connection con = new Connection(1, "192.168.0.10", 1020);
    pool.add(con);
    assertEquals(con, pool.get(con.getId()));
    pool.add(con);
    assertEquals(con, pool.get(con.getId()));

  }
  
  @Test
  void testStackFill() {
    fillStack();
    Arrays.stream(arrayConn).forEach(p->assertEquals(p, pool.get(p.getId())));
    assertEquals(con3, pool.get(con3.getId()));
  }
  
  @Test
  void testIterator() {
    fillStack();
    int cnt = 4;
    for (Connection connection : pool) {
      assertEquals(connection, arrayConn[cnt--]);
    }
  }
  
  @Test
  void testOverStack() {
    fillStack();
    Connection con = new Connection(6, "192.192.192.192", 7777);
    pool.add(con);
    assertEquals(con, pool.get(con.getId()));
    try {
      pool.get(con1.getId());
      fail();
    } catch (NoSuchElementException e) {
      System.out.println(e);
    }

  }
  
  void fillStack() {
    Arrays.stream(arrayConn).forEach(p->pool.add(p));
  }
  
  
    

}