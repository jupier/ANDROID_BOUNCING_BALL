package com.pieropan.julien.bouncingball.listview;

public class MapRow {

	// FIELDS
	
	private String mapName = null;
	private String mapHighScore = null;
	
	// METHODS
	
	public MapRow(String mapName, String mapHighScore)
	{
		this.mapHighScore = mapHighScore;
		this.mapName = mapName;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getMapHighScore() {
		return mapHighScore;
	}

	public void setMapHighScore(String mapHighScore) {
		this.mapHighScore = mapHighScore;
	}
	
	
	
}
