package com.bean.properties.connections;

import java.util.Properties;

public class ConnectionBD {
	
	private Properties properties;

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public ConnectionBD() {
	}

	public ConnectionBD(Properties properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "ConnectionBD [properties=" + properties + "]";
	}
	
	

}
