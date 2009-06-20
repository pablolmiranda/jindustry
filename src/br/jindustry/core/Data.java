package br.jindustry.core;

public class Data {
	
	private long 	id;
	private String 	name;
	private String 	value;
	private long	timestamp;
	
	public Data(long id, String name, String value, long timestamp){
		this.id 		= id;
		this.name		= name;
		this.value		= value;
		this.timestamp	= timestamp;
	}
	
	public long getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public long getTimestamp(){
		return this.timestamp;
	}
}