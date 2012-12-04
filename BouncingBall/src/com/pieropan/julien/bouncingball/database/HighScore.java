package com.pieropan.julien.bouncingball.database;

public class HighScore {

	// FIELDS
	
	private String map = null;
	private String world = null;
	private Integer timer = null;
	
	// METHODS
	
	public HighScore()
	{
		
	}
	
	public HighScore(String world, String map, Integer timer)
	{
		this.map = map;
		this.world = world;
		this.timer = timer;
	}

	// GETTERS / SETTERS
	
	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	public Integer getTimer() {
		return timer;
	}

	public void setTimer(Integer timer) {
		this.timer = timer;
	}
	
	
	
}
