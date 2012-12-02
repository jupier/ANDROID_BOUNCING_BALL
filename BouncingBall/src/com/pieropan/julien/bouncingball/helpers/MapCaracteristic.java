package com.pieropan.julien.bouncingball.helpers;

import java.io.Serializable;

public class MapCaracteristic implements Serializable {

	// CONSTANTS
	
	private static final long serialVersionUID = 1L;
	
	// FIELDS
	
	private String name;
	private Integer timer;
	
	// METHODS
	
	public MapCaracteristic(String name, String timer)
	{
		this.name = name;
		this.timer = new Integer(timer);
	}
	
	public String getName() {
		return name;
	}

	public Integer getTimer() {
		return timer;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTimer(Integer timer) {
		this.timer = timer;
	}
}
