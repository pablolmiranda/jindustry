package br.jindustry.dataacquisition;

import br.jindustry.core.Driver;

public abstract class IODriver implements Driver{
	public abstract void activeDriver();
	public abstract void inactiveDriver();
}
