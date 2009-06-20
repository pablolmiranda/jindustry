package br.jindustry.dataacquisition.tcpip;

import br.jindustry.dataacquisition.IODriverConfiguration;

public class TcpIpIODriverConfiguration extends IODriverConfiguration {
	
	private long acquisitionInterval;
	private String address;
	private String name;
	private int	port;
	
	public TcpIpIODriverConfiguration(String name, long acquisitionInterval, String address, int port){
		this.name = name;
		this.acquisitionInterval = acquisitionInterval;
		this.address = address;
		this.port = port;
	}
	
	public String getAddress(){
		return this.address;
	}
	
	public long getInterval(){
		return this.acquisitionInterval;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPort(){
		return this.port;
	}
}
