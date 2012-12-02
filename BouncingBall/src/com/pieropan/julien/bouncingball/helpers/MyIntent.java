package com.pieropan.julien.bouncingball.helpers;

import java.io.Serializable;

public class MyIntent implements Serializable {

	// CONSTANTS
	
	private static final long serialVersionUID = 1L;

	// FIELDS
	
	private String world = null;
	private MapCaracteristic map = null;
	
	private boolean music = true;
	private boolean sounds = true;
	
	private Integer lifes = null;
	private Integer timer = null;

	// METHODS
	
	public MyIntent(String world, MapCaracteristic map, Integer lifes, Integer timer, boolean music, boolean sounds)
	{
		this.world = world;
		this.map = map;
		this.lifes = lifes;
		this.timer = timer;
		this.music = music;
		this.sounds = sounds;
	}
	
	// GETTERS / SETTERS
	
	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	public boolean isMusic() {
		return music;
	}

	public void setMusic(boolean music) {
		this.music = music;
	}

	public boolean isSounds() {
		return sounds;
	}

	public void setSounds(boolean sounds) {
		this.sounds = sounds;
	}

	public Integer getLifes() {
		return lifes;
	}

	public void setLifes(Integer lifes) {
		this.lifes = lifes;
	}

	public MapCaracteristic getMap() {
		return map;
	}

	public void setMap(MapCaracteristic map) {
		this.map = map;
	}

	public Integer getTimer() {
		return timer;
	}

	public void setTimer(Integer timer) {
		this.timer = timer;
	}
}
