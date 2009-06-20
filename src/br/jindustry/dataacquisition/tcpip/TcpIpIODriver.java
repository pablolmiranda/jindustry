package br.jindustry.dataacquisition.tcpip;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;

import br.jindustry.core.Data;
import br.jindustry.core.DriverConfiguration;
import br.jindustry.core.persistence.PersistenceManager;
import br.jindustry.dataacquisition.IODriver;

public class TcpIpIODriver extends IODriver{

	private PersistenceManager persistenceManager;
	private boolean active;
	private long acquisitionInterval;
	private String serverAddress;
	private int serverPort;
	private long id;
	private String name;
	
	private CountDownLatch synchronizer;
	
	public TcpIpIODriver(long id, DriverConfiguration driverConfiguration, PersistenceManager pm){
		this.id = id;
		TcpIpIODriverConfiguration configuration = (TcpIpIODriverConfiguration)driverConfiguration;
		persistenceManager = pm;
		active = false;
		name = configuration.getName();
		acquisitionInterval = configuration.getInterval();
		serverAddress = configuration.getAddress();
		serverPort = configuration.getPort();
	}
	
	public void activeDriver(){
		active = true;
	}
	
	public void inactiveDriver(){
		active = false;
	}
	
	public void run(){
		Socket socket = null;
		try {
			socket = new Socket(serverAddress, serverPort);
			DataInputStream inputStream = new DataInputStream(socket.getInputStream());
			while(active){
				getdata(inputStream);
				Thread.sleep(acquisitionInterval);
			}
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void getdata(DataInputStream inputStream) throws Exception{
		String value = inputStream.readUTF();
		saveData(value);
		synchronizer.countDown();
	}
	
	private void saveData(String value){
		Data data = createData(value);
		persistenceManager.save(data);
	}
	
	public void setSynchronizer(CountDownLatch synchronizer){
		this.synchronizer = synchronizer;
	}
	
	private Data createData(String value){
		long timestamp = Calendar.getInstance().getTimeInMillis();
		return new Data(id, name, value, timestamp);
	}
}
