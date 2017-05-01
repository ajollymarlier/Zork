package com.bayviewglen.zork;

public abstract class Enemy extends Character{
	// add boolean in range
	private String name;
	public Enemy(int healthPoints, int speed, int strength, String name) {
		super(healthPoints, speed, strength);
		this.name = name;
		// TODO Auto-generated constructor stub
	}
	public String toString(){
		return name;
	}
	

}
