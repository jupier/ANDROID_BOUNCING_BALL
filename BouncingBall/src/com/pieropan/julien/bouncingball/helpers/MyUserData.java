package com.pieropan.julien.bouncingball.helpers;

public class MyUserData {
	
	// CONSTANTS
	
	public static final String RIGHT = "right";
	public static final String LEFT = "left";
	public static final String TOP = "top";
	public static final String DOWN = "DOWN";
	
	// FIELDS
	
	private Integer id;
	
	private String prevCollide = null;
	
	private String name = null;
	private String direction = null;
	
	// METHODS
	
	private MyUserData()
	{
		
	}
	
	public MyUserData(String name)
	{
		this.name = name;
		this.direction = MyUserData.RIGHT;
		this.prevCollide = null;
	}

	public MyUserData(String name, String direction, Integer id)
	{
		this.name = name;
		this.direction = direction;
		this.prevCollide = null;
		this.id = 0;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getDirection()
	{
		return this.direction;
	}
	
	public void setDirection(String direction)
	{
		this.direction = direction;
	}
	
	public void setPrevCollide(String prev)
	{
		this.prevCollide = prev;
	}
	
	public String getPrevCollide()
	{
		return this.prevCollide;
	}
	
	public Integer getId()
	{
		return this.id;
	}
	
}
