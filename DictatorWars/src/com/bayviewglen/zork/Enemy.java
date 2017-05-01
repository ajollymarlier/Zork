package com.bayviewglen.zork;

public abstract class Enemy extends Character{
	private String name;
	private boolean inRange;
	
	public Enemy(int healthPoints, int speed, int strength, String name, boolean inRange) {
		super(healthPoints, speed, strength);
		this.name = name;
		this.inRange = inRange;
	}
	
	public String toString(){
		return name;
	}

	public boolean getInRange() {
		return inRange;
	}
	

}
