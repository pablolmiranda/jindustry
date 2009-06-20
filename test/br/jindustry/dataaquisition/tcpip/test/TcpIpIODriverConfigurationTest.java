package br.jindustry.dataaquisition.tcpip.test;

import org.testng.Assert;
import org.testng.annotations.*;

import br.jindustry.dataacquisition.tcpip.TcpIpIODriverConfiguration;

public class TcpIpIODriverConfigurationTest {

	private TcpIpIODriverConfiguration dc;
	private long acquisitionInterval;
	private String address;
	private String name;
	private int	port;
	
	@BeforeClass
	public void init(){
		acquisitionInterval	= 1000;
		address	= "localhost";
		name = "TCP-IP driver 1";
		port = 4096;
		dc	= new TcpIpIODriverConfiguration(name, acquisitionInterval, address, port);
	}
	
	@Test
	public void get_driver_configuration_acquisition_interval(){
		Assert.assertEquals(acquisitionInterval, dc.getInterval());
	}
	
	@Test
	public void get_driver_configuration_address(){
		Assert.assertEquals(address, dc.getAddress());
	}
	
	@Test
	public void get_driver_configuration_port(){
		Assert.assertEquals(port, dc.getPort());
	}
}
