package br.jindustry.server.test;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import org.testng.Assert;
import org.testng.annotations.*;
import br.jindustry.server.TcpIpServer;

public class TcpIpServerTest {

	private String serverAddress;
	private int serverPort;
	private TcpIpServer server;
	private int[] values;
	
	@BeforeClass
	public void init(){
		serverAddress = "localhost";
		serverPort = 4505;
		values = new int[]{1, 2, 3, 4, 5};
		server = new TcpIpServer(serverPort, values);
		server.start();
	}
	
	@Test(timeOut = 5000)
	public void test_received_values(){
		Socket socket = null;
		try {
			socket = new Socket(serverAddress, serverPort);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			for( int v: values){
				String value = in.readUTF();
				Assert.assertEquals(String.valueOf(v), value);
			}
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Assert.fail();
		}
	}
	
}
