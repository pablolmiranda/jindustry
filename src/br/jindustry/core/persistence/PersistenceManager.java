package br.jindustry.core.persistence;

import java.util.List;

import br.jindustry.core.Data;

public abstract class PersistenceManager {
	
	public abstract void close();
	public abstract int count(long dataId);
	public abstract Data load(long dataId);
	public abstract List<Data> loadHistorical(long dataId);
	public abstract void save(Data data);
	
}
