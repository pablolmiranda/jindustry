package br.jindustry.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpIpServer extends Thread{
	
	private int port;
	private int[] values;
	private int valuesIndex;
	
	public TcpIpServer(int port){
		values = new int[]{-1};
	}
	
	public TcpIpServer(int port, int[] values){
		this.port = port;
		this.values = values;
		valuesIndex = 0;
	}
	
	public void run(){
		System.out.println("Initializing server on port " + port);
		ServerSocket server = null;
		Socket socket = null;
		try {
			server = new ServerSocket(port);
			socket = server.accept();
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			while(true){
				out.writeUTF(String.valueOf(values[valuesIndex]));
				incrementCircularIndex();
			}
		} catch (Exception e) {
			//e.printStackTrace();
		} finally{
			System.out.println("Closing server...");
			try {
				socket.close();
				server.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	private void incrementCircularIndex(){
		valuesIndex++;
		if(valuesIndex >= values.length)
			valuesIndex = 0;
	}
}
