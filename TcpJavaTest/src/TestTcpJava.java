import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import telran.net.TcpJavaClient;

class TestTcpJava extends TcpJavaClient {

	
	protected TestTcpJava() throws Exception {
		super("localhost", 5000);
	}

	@Test
	void reverseTest() throws Exception {
		String response = send("reverse", "hello");
		assertEquals("olleh", response);
	}
	
	@Test
	void lengthTest() throws Exception {
		int response = send("length", "hello");
		assertEquals(5, response);
	}
	
	@Test
	void wrongRequestTest() {
		try {
			send("123", "hello");
			fail("Shoude be Exception");
		} catch (IllegalArgumentException e) {
			// OK
		} catch (Exception e) {
			fail("Shoude be IllegalArgumentException");
		}
	}
	
	@AfterEach
	void disconnect() throws IOException {
		close();
	}

}
