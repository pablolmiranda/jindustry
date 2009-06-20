package br.jindustry.dataacquisition;

import br.jindustry.core.DriverConfiguration;

public abstract class IODriverConfiguration implements DriverConfiguration{
	public abstract long getInterval();
}
