package br.jindustry.core.persistence.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.testng.*;
import org.testng.annotations.*;

import br.jindustry.core.Data;
import br.jindustry.core.persistence.*;
import br.jindustry.core.persistence.db4o.*;

public class Db4oPersistenceManagerTest {
	
	private PersistenceManager pm;
	
	public Data createData(){
		long 	id		= 1;
		String 	name	= "data01";
		String 	value	= "01";
		Date 	date	= Calendar.getInstance().getTime();
		long	timestamp	= date.getTime();
		return new Data(id, name, value, timestamp);
	}
	
	@BeforeClass
	public void createPersistenceManager(){
		pm	= new Db4oPersistenceManager("jindustry");
	}
	
	@Test
	public void save_data_to_database(){
		Data data	= createData();
		pm.save(data);
	}
	
	@Test
	public void load_data_from_database(){
		Data data	= createData();
		Data dbData	= pm.load(data.getId());
		Assert.assertEquals(data.getId(), dbData.getId());
	}
	
	@Test
	public void dont_save_two_object_with_same_id_on_db(){
		Data data1	= createData();
		pm.save(data1);
		Data data2	= createData();
		pm.save(data2);
		Assert.assertEquals(pm.count(data2.getId()), 1);
	}
	
	@Test
	public void save_data_in_historial_database(){
		Data oldData	= createData();
		pm.save(oldData);
		Data newData	= createData();
		pm.save(newData);
		List<Data> datas	= pm.loadHistorical(newData.getId());
		Assert.assertEquals(datas.get(0).getTimestamp(), oldData.getTimestamp());
	}
	
	@AfterClass
	public void destroy_persistence_manager(){
		pm.close();
		pm	= null;
		
	}
}
