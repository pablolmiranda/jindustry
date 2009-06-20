package br.jindustry.core.persistence.db4o;

import java.util.List;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import br.jindustry.core.Data;
import br.jindustry.core.persistence.PersistenceManager;

public final class Db4oPersistenceManager extends PersistenceManager{
	
	private ObjectContainer historicalContainer;
	private ObjectContainer realtimeContainer;
	
	
	public Db4oPersistenceManager(String databaseName){
		String historicalDatabaseName	= databaseName + "_historical.db4o";
		databaseName					= databaseName + ".db4o";
		realtimeContainer				= Db4o.openFile(databaseName);		
		historicalContainer				= Db4o.openFile(historicalDatabaseName);
	}
	
	public void close(){
		realtimeContainer.close();
		historicalContainer.close();
	}
	
	public int count(long dataId){
		Data data = new Data(dataId, null, null, 0);
		return query(data).size();
	}
	
	private Data createData(long dataId){
		return new Data(dataId, null, null, 0);
	}
	
	public void delete(long dataId){
		ObjectSet<Data> results = query(createData(dataId));
		for( Data data: results)
			realtimeContainer.delete(data);
	}
	
	public void deleteHistorical(long dataId){
		List<Data> historicalData = this.loadHistorical(dataId);
		for( Data data: historicalData)
			historicalContainer.delete(data);
	}
	
	public boolean exist(long dataId){
		return query(createData(dataId)).size() > 0;
	}
	
	public Data load(long dataId){
		return query(createData(dataId)).get(0);
	}
	
	public List<Data> loadHistorical(long dataId){
		Query query	= historicalContainer.query();
		query.constrain(createData(dataId));
		query.descend("timestamp").orderDescending();
		ObjectSet<Data> results	= query.execute();
		return results;
	}
	
	private void moveToHistorical(long dataId){
		ObjectSet<Data> results = query(createData(dataId));
		for( Data data: results){
			historicalContainer.store(data);
			realtimeContainer.delete(data);
		}
		realtimeContainer.commit();
		historicalContainer.commit();
	}
	
	private ObjectSet<Data> query(Data data){
		ObjectSet<Data> results = realtimeContainer.queryByExample(data);
		return results;
	}
	
	public void save(Data data){
		if(exist(data.getId())){
			moveToHistorical(data.getId());
		}
		realtimeContainer.store(data);
		realtimeContainer.commit();
	}
}
