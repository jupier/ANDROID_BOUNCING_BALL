package com.pieropan.julien.bouncingball.helpers;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MapSelector {
	
	// CONSTANTS
	
	// LIST OF WORLDS
	private final String[] WORLD_NAMES = 	{
											"island", 
											"cave"
											};
	
	//	LIST OF MAPS IN THE WORLDS
	private final String[][][] LEVEL_NAMES = 	{
											{{"map_island_beginner", "4000"}, {"map_island", "6000"}},
											{{"map_cave", "4000"}}
											};
	
	private static MapSelector singleton = null;
	
	// FIELDS
	
	private Map<String, List<MapCaracteristic>> world = new Hashtable<String, List<MapCaracteristic>> ();
	
	// METHODS
	
	private MapSelector()
	{
		int i = 0;

		for (String world_name : WORLD_NAMES)
		{
			List<MapCaracteristic> levels = new ArrayList<MapCaracteristic>();
			for (String level_name[] : LEVEL_NAMES[i])
				levels.add(levels.size(), new MapCaracteristic(level_name[0], level_name[1]));
			world.put(world_name, levels);
			i++;
		}
	}
	
	public static MapSelector getInstance()
	{
		if (MapSelector.singleton == null)
		{
			MapSelector.singleton = new MapSelector();
		}
		
		return MapSelector.singleton;
	}
	
	public List<String> getWorlds()
	{
		List<String> worldName = new ArrayList<String>();
		
		for (String key : world.keySet()) worldName.add(0, key);

		return worldName;
	}
	
	public List<String> getMaps(String worldName)
	{
		List<String> mapName = new ArrayList<String>();
		
		for (MapCaracteristic map : world.get(worldName))
			mapName.add(mapName.size(), map.getName());
		
		return mapName;
 	}
	
	public MapCaracteristic getCarac(String worldName, String mapName)
	{
		for (MapCaracteristic map : world.get(worldName))
		{
			if (map.getName().equals(mapName))
			{
				return map;
			}
		}
		return null;
	}
	
	public MapCaracteristic nextCarac(String worldName, String mapName)
	{
		Integer index = 0;
		
		for (MapCaracteristic map : world.get(worldName))
		{
			if (map.getName().equals(mapName))
			{
				index = world.get(worldName).indexOf(map);
			}
		}
		if ((index + 1) >= world.get(worldName).size())
			return null;
		return world.get(worldName).get(index + 1);
	}
}
