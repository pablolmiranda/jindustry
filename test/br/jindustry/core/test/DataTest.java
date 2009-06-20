package br.jindustry.core.test;

import java.util.Calendar;
import java.util.Date;

import org.testng.annotations.*;
import org.testng.Assert;
import br.jindustry.core.Data;

public class DataTest {
	
	private long 	id;		
	private String 	name;
	private String 	value;
	private long 	timestamp;
	private Data	data;
	
	@BeforeTest
	public void init_data(){
		this.id			= 1;
		this.name		= "data01";
		this.value		= "01";
		Date	date	= Calendar.getInstance().getTime();
		this.timestamp	= date.getTime();
		this.data	= new Data(id, name, value, timestamp);
	}
	
	@Test
	public void get_data_id(){
		Assert.assertEquals(data.getId(), id);
	}
	
	@Test
	public void get_data_name(){
		Assert.assertEquals(data.getName(), name);
	}
	
	@Test
	public void get_data_value(){
		Assert.assertEquals(data.getValue(), value);
	}
	
	@Test
	public void get_data_timestamp(){
		Assert.assertEquals(data.getTimestamp(), this.timestamp);
	}
}
