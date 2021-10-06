package telran.net;

import java.io.*;
import java.net.*;

import telran.net.dto.*;
import telran.net.util.UdpUtils;


public class UdpJavaClient implements Closeable {
	
	private static final int MAX_PACKET_LENGTH = 100000;
	private DatagramSocket socket;
	private InetAddress host;
	private int port;

	protected UdpJavaClient(String host, int port) throws Exception {
		this.host = InetAddress.getByName(host);
		this.port = port;
		socket = new DatagramSocket();
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T send(String requestType, Serializable date) throws Exception {
		byte[] bufferSend = null;
		byte[] bufferReceive = new byte[MAX_PACKET_LENGTH];
		
		RequestJava request = new RequestJava(requestType, date);
		bufferSend = UdpUtils.getByteArray(request);
		DatagramPacket packetSend = new DatagramPacket(bufferSend, bufferSend.length, host, port);
		socket.send(packetSend);
		
		DatagramPacket response = new DatagramPacket(bufferReceive, bufferReceive.length);
        socket.receive(response);
        ResponseJava packetResponse = (ResponseJava) UdpUtils.getSerializableFromByteArray(bufferReceive, bufferReceive.length);
        
        if (packetResponse.code != ResponseCode.OK) {
			throw (Exception) packetResponse.data;
		}
		
		return (T) packetResponse.data;
	}
	
	@Override
	public void close() throws IOException {
		socket.close();
	}

}
