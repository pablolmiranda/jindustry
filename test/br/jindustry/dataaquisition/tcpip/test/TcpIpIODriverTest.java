package br.jindustry.dataaquisition.tcpip.test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.testng.Assert;
import org.testng.annotations.*;

import br.jindustry.core.Data;
import br.jindustry.core.DriverConfiguration;
import br.jindustry.core.persistence.PersistenceManager;
import br.jindustry.core.persistence.db4o.Db4oPersistenceManager;
import br.jindustry.dataacquisition.IODriver;
import br.jindustry.dataacquisition.tcpip.TcpIpIODriver;
import br.jindustry.dataacquisition.tcpip.TcpIpIODriverConfiguration;
import br.jindustry.server.TcpIpServer;

public class TcpIpIODriverTest {
	
	private PersistenceManager pm;
	private DriverConfiguration driverConfiguration;
	private IODriver driver;
	private long driverId;
	private String driverName;
	private int[] serverValues;
	
	private String serverAddress;
	private int serverPort;
	
	@Test(timeOut = 15000)
	public void test_driver_data_acquisition() throws Exception{
		CountDownLatch synchronizer = new CountDownLatch(1);
		((TcpIpIODriver)driver).setSynchronizer(synchronizer);
		driver.activeDriver();
		new Thread(driver).start();
		synchronizer.await();
		driver.inactiveDriver();
		Data lastData = pm.load(driverId);
		int receivedValue = Integer.parseInt(lastData.getValue());
		Assert.assertEquals(receivedValue, serverValues[0]);
	}
	
	@Test(timeOut = 15000)
	public void test_driver_data_acquisition_for_five_loops() throws Exception{
		CountDownLatch synchronizer = new CountDownLatch(5);
		((TcpIpIODriver)driver).setSynchronizer(synchronizer);
		driver.activeDriver();
		new Thread(driver).start();
		synchronizer.await();
		driver.activeDriver();
		List<Data> historicalData = pm.loadHistorical(driverId);
		Assert.assertEquals(historicalData.size(), serverValues.length);
	}
	
	@BeforeClass
	public void init(){
		initDriverConfiguration();
		initPersistenceManager();
		initServer();
		driverId = 1;
		driver = new TcpIpIODriver(driverId, driverConfiguration, pm);
	}
	
	public void initDriverConfiguration(){
		serverAddress = "localhost";
		serverPort = 4500;
		driverName = "TCP-IP Driver 1";
		long driverAcquisitionInterval = 100;
		driverConfiguration = new TcpIpIODriverConfiguration(driverName, driverAcquisitionInterval, serverAddress, serverPort);
	}
	
	public void initPersistenceManager(){
		String databaseName = "jindustry";
		pm = new Db4oPersistenceManager(databaseName);
	}
	
	public void initServer(){
		serverAddress = "localhost";
		serverPort = 4500;
		serverValues = new int[]{1, 2, 3, 4, 5};
		TcpIpServer server = new TcpIpServer(serverPort, serverValues);
		server.start();
	}
	
	@BeforeMethod
	public void cleanHistoricalDatabase(){
		((Db4oPersistenceManager)pm).deleteHistorical(driverId);
	}
}
